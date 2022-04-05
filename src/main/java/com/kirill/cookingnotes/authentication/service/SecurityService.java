package com.kirill.cookingnotes.authentication.service;

import com.kirill.cookingnotes.authentication.dto.AuthResponseDTO;
import com.kirill.cookingnotes.authentication.dto.LoginRequestDTO;
import com.kirill.cookingnotes.authentication.dto.PasswordUpdateRequestDTO;
import com.kirill.cookingnotes.authentication.dto.SignUpRequestDTO;
import com.kirill.cookingnotes.exception.BadRequestException;
import com.kirill.cookingnotes.profile.entity.User;
import com.kirill.cookingnotes.profile.repository.UserRepository;
import com.kirill.cookingnotes.profile.service.UserService;
import com.kirill.cookingnotes.security.AuthProvider;
import com.kirill.cookingnotes.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final UserService userService;

    private final Map<UUID, ActivatingUser> verifying = new HashMap<>();
    private final List<String> reserved = new ArrayList<>();

    @Value("${frontend.address}")
    private String frontendAddress;

    synchronized void addUser(ActivatingUser user, UUID id) {
        verifying.put(id, user);
        reserved.add(user.getUser().getEmail());
    }

    public AuthResponseDTO authenticate(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return new AuthResponseDTO(token);
    }

    public void register(SignUpRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail()) || reserved.contains(request.getEmail())) {
            throw new BadRequestException("Email address (" + request.getEmail() + ") already in use.", "EMAIL_IN_USE");
        }

        User user = new User();
        user.setDisplayName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UUID id = UUID.randomUUID();
        addUser(new ActivatingUser(user), id);

        SimpleMailMessage message = new SimpleMailMessage();

        String location = ServletUriComponentsBuilder.fromUriString(frontendAddress).path("/verify").query("id={id}")
                .buildAndExpand(id).toUriString();

        message.setTo(request.getEmail());
        message.setSubject("Cooking Notes");
        message.setText(location);

        this.mailSender.send(message);
    }

    public void activate(UUID id) {
        if (!verifying.containsKey(id) || userRepository.existsByEmail(verifying.get(id).getUser().getEmail())) {
            throw new BadRequestException("This account (" + verifying.get(id).getUser().getEmail() +
                    ") is reserved, already verified or doesn't exist", "EMAIL_IN_USE");
        }
        userRepository.save(verifying.get(id).getUser());
        reserved.remove(verifying.get(id).getUser().getEmail());
        verifying.remove(id);
    }

    public void updatePassword(PasswordUpdateRequestDTO request) {
        User user = userService.getAuthenticatedUser();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Old password is invalid", "INVALID");
        }
        System.out.println("all ok");
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
package com.kirill.cookingnotes.profile.service;

import com.kirill.cookingnotes.authentication.service.AuthService;
import com.kirill.cookingnotes.exception.BadRequestException;
import com.kirill.cookingnotes.exception.ResourceNotFoundException;
import com.kirill.cookingnotes.profile.dto.ProfilePublicRequestDTO;
import com.kirill.cookingnotes.profile.entity.User;
import com.kirill.cookingnotes.profile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public User getAuthenticatedUser() {
        return getUser(authService.getAuthenticatedUserId());
    }

    public User getUser(Long id) {
        if(id==null)
            return null;
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
    }

    public void edit(ProfilePublicRequestDTO request, Long id) {
        Long userId = authService.getAuthenticatedUserId();
        if (!userId.equals(id))
            throw new BadRequestException("This profile can be edited only by user with id "+id, "INVALID");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        user.setDisplayName(request.getName());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        user.setAbout(request.getAbout());
        user.setTwitterLink(request.getTwitterLink());
        user.setInstagramLink(request.getInstagramLink());
        user.setFacebookLink(request.getFacebookLink());
        user.setCover(request.getCover());
        user.setAvatar(request.getAvatar());
        userRepository.save(user);
    }

    public void remove(Long id) {
        Long authId = authService.getAuthenticatedUserId();
        if (!authId.equals(id)) {
            return;
        }
        User user = userRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", authId));
        userRepository.delete(user);
    }
}

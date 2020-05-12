package com.kirill.cookingnotes.authentication.controller;

import com.kirill.cookingnotes.authentication.dto.LoginRequestDTO;
import com.kirill.cookingnotes.authentication.dto.PasswordUpdateRequestDTO;
import com.kirill.cookingnotes.authentication.dto.SignUpRequestDTO;
import com.kirill.cookingnotes.authentication.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SecurityService securityService;

    @Operation(summary = "Authenticates user and returns JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(securityService.authenticate(loginRequest));
    }

    @Operation(summary = "Registers user")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDTO request) {
        securityService.register(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Verifies user")
    @GetMapping("/verify")
    public ResponseEntity<?> activate(@RequestParam UUID id) {
        securityService.activate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @SecurityRequirement(name = "token")
    @Operation(summary = "Updates logged in user's password")
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequestDTO request) {
        securityService.updatePassword(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

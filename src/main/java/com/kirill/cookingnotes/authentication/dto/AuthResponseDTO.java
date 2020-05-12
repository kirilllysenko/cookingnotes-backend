package com.kirill.cookingnotes.authentication.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthResponseDTO {
    private final String accessToken;
    private String tokenType = "Bearer";
}
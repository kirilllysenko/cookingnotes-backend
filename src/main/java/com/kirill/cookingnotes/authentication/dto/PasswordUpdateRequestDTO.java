package com.kirill.cookingnotes.authentication.dto;

import lombok.Data;

@Data
public class PasswordUpdateRequestDTO {
    private String oldPassword;
    private String newPassword;
}

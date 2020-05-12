package com.kirill.cookingnotes.profile.dto;

import lombok.Data;

@Data
public class UpdatePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
}

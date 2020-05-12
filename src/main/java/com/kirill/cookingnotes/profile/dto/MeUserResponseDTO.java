package com.kirill.cookingnotes.profile.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeUserResponseDTO {
    private Long id;
    private String name;
    private byte[] avatar;
}

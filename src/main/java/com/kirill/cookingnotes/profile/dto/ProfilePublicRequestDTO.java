package com.kirill.cookingnotes.profile.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfilePublicRequestDTO {
    @NotBlank
    private String name;

    private String country;
    private String city;
    private String about;
    private String facebookLink;
    private String twitterLink;
    private String instagramLink;
    private byte[] avatar;
    private byte[] cover;
}

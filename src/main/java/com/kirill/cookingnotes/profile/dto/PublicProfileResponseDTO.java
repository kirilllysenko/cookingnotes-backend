package com.kirill.cookingnotes.profile.dto;

import lombok.Data;

@Data
public class PublicProfileResponseDTO {
    private final Long id;
    private final String name;
    private final String country;
    private final String city;
    private final String about;
    private final String blogLink;
    private final String twitterLink;
    private final String facebookLink;
    private final String instagramLink;
    private final byte[] avatar;
    private final byte[] cover;
}

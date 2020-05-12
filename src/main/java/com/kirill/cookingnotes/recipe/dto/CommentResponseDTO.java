package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CommentResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private byte[] userAvatar;
    private String text;
    private Date date;
    private int rating;
}

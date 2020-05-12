package com.kirill.cookingnotes.recipe.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddCommentResponseDTO {
    private final String text;
    private final Date date;
    private final int rating;
    private final byte[] avatar;
    private final String name;
    private final int recipeRating;
}

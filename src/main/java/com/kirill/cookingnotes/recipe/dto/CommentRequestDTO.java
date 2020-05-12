package com.kirill.cookingnotes.recipe.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequestDTO {
    @NotBlank
    private String text;

    @NotNull
    private int rating;

    @NotNull
    private Long recipeId;
}

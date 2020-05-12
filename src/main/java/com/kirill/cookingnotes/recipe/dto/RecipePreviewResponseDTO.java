package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipePreviewResponseDTO {
    private Long id;
    private int rating;
    private byte[] imagePreview;
    private String title;
}

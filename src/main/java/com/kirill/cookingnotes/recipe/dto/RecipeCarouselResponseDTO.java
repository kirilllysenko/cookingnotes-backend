package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecipeCarouselResponseDTO {
    private Long id;
    private String description;
    private byte[] image;
    private String title;
}

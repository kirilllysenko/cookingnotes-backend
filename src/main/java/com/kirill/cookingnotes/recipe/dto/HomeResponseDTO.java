package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HomeResponseDTO {
    private List<RecipeCarouselResponseDTO> officialRecipes;
    private List<RecipePreviewResponseDTO> bestRecipes;
}

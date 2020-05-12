package com.kirill.cookingnotes.recipe.converter;

import com.kirill.cookingnotes.recipe.dto.RecipePreviewResponseDTO;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipesToPreviewConverter implements Converter<Recipe, RecipePreviewResponseDTO> {

    @Override
    public RecipePreviewResponseDTO convert(Recipe recipe) {
        return RecipePreviewResponseDTO
                        .builder()
                        .id(recipe.getId())
                        .rating(recipe.getRating())
                        .imagePreview(recipe.getImagePreview())
                        .title(recipe.getTitle())
                        .build();
    }
}

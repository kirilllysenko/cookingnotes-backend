package com.kirill.cookingnotes.recipe.converter;

import com.kirill.cookingnotes.recipe.dto.RecipeCarouselResponseDTO;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipesToCarouselConverter implements Converter<Recipe, RecipeCarouselResponseDTO> {

    @Override
    public RecipeCarouselResponseDTO convert(Recipe recipe){
        return RecipeCarouselResponseDTO
                .builder()
                .description(recipe.getDescription())
                .id(recipe.getId())
                .image(recipe.getImage())
                .title(recipe.getTitle())
                .build();
    }
}

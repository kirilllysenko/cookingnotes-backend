package com.kirill.cookingnotes.recipe.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RecipeRequestDTO {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Boolean isStepsWithImage;

    @NotNull
    private byte[] image;

    @NotNull
    private byte[] imagePreview;

    @NotNull
    private int portions;

    @NotNull
    private int cookTime;

    private int prepareTime;

    @NotBlank
    private List<Integer> categories;

    @NotNull
    private List<StepDTO> steps;

    @NotNull
    private List<IngredientDTO> ingredients;
}

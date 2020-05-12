package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class RecipeResponseDTO {
    private Long id;
    private String title;
    private String description;
    private byte[] userAvatar;
    private String userName;
    private Long userId;
    private byte[] image;
    private byte[] imagePreview;
    private Integer portions;
    private Integer cookTime;
    private Integer prepareTime;
    private Integer rating;
    private Date date;
    private Boolean isStepsWithImage;
    private List<StepDTO> steps;
    private List<CategoryResponseDTO> categories;
    private List<IngredientDTO> ingredients;
    private Boolean isFavourite;
    private Boolean isMadeIt;
    private CommentResponseDTO userComment;
}

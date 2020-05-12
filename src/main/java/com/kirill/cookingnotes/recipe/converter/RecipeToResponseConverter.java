package com.kirill.cookingnotes.recipe.converter;

import com.kirill.cookingnotes.authentication.service.AuthService;
import com.kirill.cookingnotes.profile.service.UserService;
import com.kirill.cookingnotes.recipe.dto.*;
import com.kirill.cookingnotes.recipe.entity.Category;
import com.kirill.cookingnotes.recipe.entity.Ingredient;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.entity.Step;
import com.kirill.cookingnotes.recipe.repository.CommentRepository;
import com.kirill.cookingnotes.recipe.specification.CommentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeToResponseConverter implements Converter<Recipe, RecipeResponseDTO> {

    private final CommentRepository commentRepository;
    @Lazy
    private final AuthService authService;
    private final UserService userService;
    @Lazy
    private final ConversionService conversionService;

    @Override
    public RecipeResponseDTO convert(Recipe recipe) {
        List<StepDTO> steps = recipe
                .getSteps()
                .stream()
                .map((Step val) -> StepDTO
                        .builder()
                        .image(val.getImage())
                        .text(val.getText())
                        .build())
                .collect(Collectors.toList());

        List<IngredientDTO> ingredients = recipe
                .getIngredients()
                .stream()
                .map((Ingredient val) -> IngredientDTO
                        .builder()
                        .amount(val.getAmount())
                        .name(val.getName())
                        .unit(val.getUnit().getId())
                        .build())
                .collect(Collectors.toList());

        List<CategoryResponseDTO> categories = recipe
                .getCategories()
                .stream()
                .map((Category val) -> CategoryResponseDTO
                        .builder()
                        .id(val.getId())
                        .name(val.getName())
                        .build())
                .collect(Collectors.toList());

        Long authenticatedUserId=authService.getAuthenticatedUserId();

        return RecipeResponseDTO
                .builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .userAvatar(recipe.getUser().getAvatar())
                .userName(recipe.getUser().getDisplayName())
                .userId(recipe.getUser().getId())
                .image(recipe.getImage())
                .imagePreview(recipe.getImagePreview())
                .portions(recipe.getPortions())
                .cookTime(recipe.getCookTime())
                .prepareTime(recipe.getPrepTime())
                .rating(recipe.getRating())
                .date(recipe.getDate())
                .isStepsWithImage(recipe.isStepsWithImage())
                .steps(steps)
                .ingredients(ingredients)
                .categories(categories)
                .userComment(conversionService.convert(commentRepository.findAll(Specification
                        .where(CommentSpecification.byRecipe(recipe))
                        .and(CommentSpecification.byUserId(authenticatedUserId))), CommentResponseDTO.class))
                .isFavourite(recipe.getFavourites().contains(userService.getAuthenticatedUser()))
                .isMadeIt(recipe.getMadeit().contains(userService.getAuthenticatedUser()))
                .build();
    }
}

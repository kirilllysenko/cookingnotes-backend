package com.kirill.cookingnotes.recipe.converter;

import com.kirill.cookingnotes.profile.entity.User;
import com.kirill.cookingnotes.profile.service.UserService;
import com.kirill.cookingnotes.recipe.dto.IngredientDTO;
import com.kirill.cookingnotes.recipe.dto.RecipeRequestDTO;
import com.kirill.cookingnotes.recipe.dto.StepDTO;
import com.kirill.cookingnotes.recipe.entity.Category;
import com.kirill.cookingnotes.recipe.entity.Ingredient;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.entity.Step;
import com.kirill.cookingnotes.recipe.repository.CategoryRepository;
import com.kirill.cookingnotes.recipe.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RequestToRecipeConverter implements Converter<RecipeRequestDTO, Recipe> {

    private final CategoryRepository categoryRepository;
    private final UnitRepository unitRepository;
    private final UserService userService;

    @Override
    public Recipe convert(RecipeRequestDTO request) {
        User user = userService.getAuthenticatedUser();
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAllById(request.getCategories()).forEach(categories::add);
        Recipe recipe = Recipe.builder()
                .id(request.getId())
                .user(user)
                .categories(categories)
                .description(request.getDescription())
                .isStepsWithImage(request.getIsStepsWithImage())
                .cookTime(request.getCookTime())
                .image(request.getImage())
                .imagePreview(request.getImagePreview())
                .portions(request.getPortions())
                .title(request.getTitle())
                .prepTime(request.getPrepareTime())
                .build();
        List<Ingredient> ingredients = request
                .getIngredients()
                .stream()
                .map((IngredientDTO dto) -> Ingredient
                        .builder()
                        .amount(dto.getAmount())
                        .name(dto.getName())
                        .unit(unitRepository.findById(dto.getUnit()).get())
                        .recipe(recipe)
                        .build())
                .collect(Collectors.toList());
        List<Step> steps = request
                .getSteps()
                .stream()
                .map((StepDTO dto) -> Step
                        .builder()
                        .recipe(recipe)
                        .text(dto.getText())
                        .image(dto.getImage())
                        .build())
                .collect(Collectors.toList());
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        return recipe;
    }
}

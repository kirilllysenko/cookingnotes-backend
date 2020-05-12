package com.kirill.cookingnotes.recipe.repository;

import com.kirill.cookingnotes.recipe.entity.Ingredient;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findAllByRecipe(Recipe recipe);
}

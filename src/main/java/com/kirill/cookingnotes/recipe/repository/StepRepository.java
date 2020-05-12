package com.kirill.cookingnotes.recipe.repository;

import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.entity.Step;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StepRepository extends CrudRepository<Step, Long> {
    List<Step> findAllByRecipe(Recipe recipe);
}

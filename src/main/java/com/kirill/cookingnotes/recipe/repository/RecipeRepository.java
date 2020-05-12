package com.kirill.cookingnotes.recipe.repository;

import com.kirill.cookingnotes.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
}

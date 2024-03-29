package com.kirill.cookingnotes.recipe.repository;

import com.kirill.cookingnotes.recipe.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();
}

package com.kirill.cookingnotes.recipe.repository;

import com.kirill.cookingnotes.recipe.entity.Comment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    List<Comment> findAll(Specification specification);
    boolean existsByUser_IdAndRecipe_Id(Long userId, Long recipeId);
}

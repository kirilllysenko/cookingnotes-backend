package com.kirill.cookingnotes.recipe.specification;

import com.kirill.cookingnotes.profile.entity.User_;
import com.kirill.cookingnotes.recipe.entity.Comment;
import com.kirill.cookingnotes.recipe.entity.Comment_;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.entity.Recipe_;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {

    public static Specification<Comment> byRecipe(Recipe recipe) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipe"), recipe);
    }

    public static Specification<Comment> byRecipeId(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Comment_.recipe).get(Recipe_.id), id);
    }

    public static Specification<Comment> byUserId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.equal(root.get(Comment_.user).get(User_.id), id);
        };
    }
}

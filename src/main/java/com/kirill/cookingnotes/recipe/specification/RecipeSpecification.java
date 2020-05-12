package com.kirill.cookingnotes.recipe.specification;

import com.kirill.cookingnotes.profile.entity.User_;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.entity.Recipe_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;


@Slf4j
public class RecipeSpecification {
    public static Specification<Recipe> byTitleContaining(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Recipe_.title), "%" + title + "%");
    }

    public static Specification<Recipe> byCategory(int category) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (category < 0) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }

    public static Specification<Recipe> byFavouritesId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Recipe_.favourites).get(User_.id), id);
    }

    public static Specification<Recipe> byMadeItId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Recipe_.madeit).get(User_.id), id);
    }

    public static Specification<Recipe> byUserId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Recipe_.favourites).get(User_.id), id);
    }

    public static Specification<Recipe> byId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Recipe_.id), id);
    }
}

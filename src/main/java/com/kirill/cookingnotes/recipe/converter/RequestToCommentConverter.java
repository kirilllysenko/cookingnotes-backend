package com.kirill.cookingnotes.recipe.converter;

import com.kirill.cookingnotes.exception.ResourceNotFoundException;
import com.kirill.cookingnotes.profile.entity.User;
import com.kirill.cookingnotes.profile.repository.UserRepository;
import com.kirill.cookingnotes.profile.service.UserService;
import com.kirill.cookingnotes.recipe.dto.CommentRequestDTO;
import com.kirill.cookingnotes.recipe.entity.Comment;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestToCommentConverter implements Converter<CommentRequestDTO, Comment> {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final UserService userService;

    @Override
    public Comment convert(CommentRequestDTO request){
        User user = userService.getAuthenticatedUser();
        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("recipe", "id", request.getRecipeId()));
        return Comment
                .builder()
                .user(user)
                .recipe(recipe)
                .rating(request.getRating())
                .text(request.getText())
                .build();
    }
}

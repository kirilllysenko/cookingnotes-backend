package com.kirill.cookingnotes.recipe.service;

import com.kirill.cookingnotes.authentication.dto.StartResponseDTO;
import com.kirill.cookingnotes.authentication.service.AuthService;
import com.kirill.cookingnotes.exception.BadRequestException;
import com.kirill.cookingnotes.exception.ResourceNotFoundException;
import com.kirill.cookingnotes.profile.entity.User;
import com.kirill.cookingnotes.profile.service.UserService;
import com.kirill.cookingnotes.recipe.dto.*;
import com.kirill.cookingnotes.recipe.entity.Category;
import com.kirill.cookingnotes.recipe.entity.Comment;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.entity.Unit;
import com.kirill.cookingnotes.recipe.repository.*;
import com.kirill.cookingnotes.recipe.specification.CommentSpecification;
import com.kirill.cookingnotes.recipe.specification.RecipeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;
    private final IngredientRepository ingredientRepository;
    private final CommentRepository commentRepository;
    private final UnitRepository unitRepository;
    private final CategoryRepository categoryRepository;
    private final AuthService authService;
    private final UserService userService;
    @Lazy
    private final ConversionService conversionService;

    public HomeResponseDTO getInitialRecipes() {
        List<RecipeCarouselResponseDTO> official = recipesToCarousel(
                recipeRepository.findAll(
                        RecipeSpecification.byUserId((long) 2),
                        PageRequest.of(0, 3, Sort.by("date"))
                ).getContent());
        List<RecipePreviewResponseDTO> best = recipesToPreview(
                recipeRepository.findAll(
                        PageRequest.of(0, 6, Sort.by("rating").descending())
                ).getContent());
        return HomeResponseDTO
                .builder()
                .bestRecipes(best)
                .officialRecipes(official)
                .build();
    }

    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("recipe", "id", id));
    }

    public Long saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe).getId();
    }

    public void editRecipe(Long id, Recipe recipe) {
        Recipe old = getRecipe(id);
        if (!old.getUser().getId().equals(authService.getAuthenticatedUserId()))
            throw new BadRequestException("Not your recipe", "INVALID");
        stepRepository.deleteAll(old.getSteps());
        ingredientRepository.deleteAll(old.getIngredients());
        BeanUtils.copyProperties(recipe, old, "id", "date", "rating");
        recipeRepository.save(old);
    }

    public void removeRecipe(Long id) {
        Recipe recipe = getRecipe(id);
        if (!recipe.getUser().getId().equals(authService.getAuthenticatedUserId())) {
            return;
        }
        recipeRepository.delete(recipe);
//        for (User user : recipe.getFavourites())
//            user.getFavourites().remove(recipe);
//        for (User user : recipe.getMadeit())
//            user.getMadeit().remove(recipe);
    }

    public void removeComment(Long recipeId, Long commentId) {
        Long userId = authService.getAuthenticatedUserId();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", userId));
        if ((!comment.getUser().getId().equals(userId) && !comment.getRecipe().getId().equals(recipeId)))
            throw new BadRequestException("Not this user's recipe or not this recipe's", "INVALID");
        commentRepository.deleteById(comment.getId());
        Recipe recipe = getRecipe(recipeId);
        int size = recipe.getComments().size();
        recipe.setRating(((recipe.getRating() * size) - comment.getRating()) / ((size != 1) ? size - 1 : 1));
        recipe.getComments().removeIf(x -> x.getId().equals(comment.getId()));
        recipeRepository.save(recipe);
    }

    public int getRating(Long recipeId) {
        return getRecipe(recipeId).getRating();
    }

    public Comment addComment(Comment comment, Long recipeId) {
        if(commentRepository.existsByUser_IdAndRecipe_Id(authService.getAuthenticatedUserId(), recipeId))
            throw new BadRequestException("Comment already exists", "EXISTS");
        Recipe recipe = getRecipe(recipeId);
        recipe.setRating(recipe.getRating() + (comment.getRating() - recipe.getRating()) / (recipe.getComments().size() + 1));
        Comment returnedComment = commentRepository.save(comment);
        recipeRepository.save(recipe);
        return returnedComment;
    }

    public void editComment(Comment comment, Long recipeId, Long commentId) {
        if (!authService.getAuthenticatedUserId().equals(comment.getUser().getId()))
            throw new BadRequestException("Not this user's recipe or not this recipe's", "INVALID");
        Recipe recipe = getRecipe(recipeId);
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        recipe.setRating((recipe.getComments().size()*recipe.getRating()-oldComment.getRating()+comment.getRating())/
                recipe.getComments().size());
        BeanUtils.copyProperties(comment, oldComment, "id", "date", "user", "recipe");
        commentRepository.save(oldComment);
        recipeRepository.save(recipe);
    }

    public void toggleFavourites(Long recipeId) {
        User user = userService.getAuthenticatedUser();
        Recipe recipe = getRecipe(recipeId);
        List<Recipe> favourites = user.getFavourites();
        if (recipe.getFavourites().contains(user)) {
            favourites.remove(recipe);
        } else {
            favourites.add(recipe);
        }
        user.setFavourites(favourites);
    }

    public void toggleMadeIt(Long recipeId) {
        User user = userService.getAuthenticatedUser();
        Recipe recipe = getRecipe(recipeId);
        List<Recipe> madeit = user.getMadeit();
        if (recipe.getMadeit().contains(user)) {
            madeit.remove(recipe);
        } else {
            madeit.add(recipe);
        }
        user.setMadeit(madeit);
    }

    public PageResponseDTO<RecipePreviewResponseDTO> searchRecipes(String title, int page, int size, int category) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        Specification<Recipe> specification = Specification
                .where(RecipeSpecification.byTitleContaining(title))
                .and(RecipeSpecification.byCategory(category));
        Page<Recipe> recipePage = recipeRepository.findAll(specification, pageable);
        return new PageResponseDTO<>(
                !recipePage.isLast(),
                recipesToPreview(recipePage.getContent())
        );
    }

    public PageResponseDTO<RecipePreviewResponseDTO> searchRecipes(String title, int page, int size, int category,
                                                                   Specification<Recipe> optional) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        Specification<Recipe> specification = Specification
                .where(RecipeSpecification.byTitleContaining(title))
                .and(RecipeSpecification.byCategory(category))
                .and(optional);
        Page<Recipe> recipePage = recipeRepository.findAll(specification, pageable);
        return new PageResponseDTO<>(
                !recipePage.isLast(),
                recipesToPreview(recipePage.getContent())
        );
    }

    public PageResponseDTO<CommentResponseDTO> searchComments(Long recipeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        Specification<Comment> specification = Specification
                .where(CommentSpecification.byRecipeId(recipeId))
                .and(Specification.not(CommentSpecification.byUserId(authService.getAuthenticatedUserId())));
        Page<Comment> commentPage = commentRepository.findAll(specification, pageable);
        return new PageResponseDTO<>(
                !commentPage.isLast(),
                commentsToResponse(commentPage.getContent())
        );
    }

    public List<RecipePreviewResponseDTO> recipesToPreview(List<Recipe> recipes) {
        return recipes
                .stream()
                .map((Recipe val) -> conversionService.convert(val, RecipePreviewResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<RecipeCarouselResponseDTO> recipesToCarousel(List<Recipe> recipes) {
        return recipes
                .stream()
                .map((Recipe val) -> conversionService.convert(val, RecipeCarouselResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<CommentResponseDTO> commentsToResponse(List<Comment> comments) {
        return comments
                .stream()
                .map((Comment val) -> conversionService.convert(val, CommentResponseDTO.class))
                .collect(Collectors.toList());
    }

    public StartResponseDTO start() {
        List<CategoryResponseDTO> categories = categoryRepository
                .findAll()
                .stream()
                .map((Category c) -> CategoryResponseDTO
                        .builder()
                        .id(c.getId())
                        .name(c.getName())
                        .build())
                .collect(Collectors.toList());
        List<UnitResponseDTO> units = unitRepository
                .findAll()
                .stream()
                .map((Unit u) -> UnitResponseDTO
                        .builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build())
                .collect(Collectors.toList());
        return StartResponseDTO
                .builder()
                .categories(categories)
                .units(units)
                .build();
    }
}

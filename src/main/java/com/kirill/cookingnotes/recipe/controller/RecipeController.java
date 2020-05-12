package com.kirill.cookingnotes.recipe.controller;

import com.kirill.cookingnotes.authentication.dto.StartResponseDTO;
import com.kirill.cookingnotes.recipe.dto.*;
import com.kirill.cookingnotes.recipe.entity.Comment;
import com.kirill.cookingnotes.recipe.entity.Recipe;
import com.kirill.cookingnotes.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final ConversionService conversionService;

    @Operation(summary = "Searches recipes by given criteria")
    @GetMapping
    @ResponseBody
    public PageResponseDTO<RecipePreviewResponseDTO> search(@RequestParam String title, @RequestParam int size,
                                                 @RequestParam int page, @RequestParam int category) {
        return recipeService.searchRecipes(title, page, size, category);
    }

    @Operation(summary = "Adds recipe")
    @SecurityRequirement(name = "token")
    @PostMapping
    public Long add(@RequestBody RecipeRequestDTO request) {
        return recipeService.saveRecipe(conversionService.convert(request, Recipe.class));
    }

    @Operation(summary = "Returns recipes on the home page")
    @GetMapping("/initialRecipes")
    @ResponseBody
    public HomeResponseDTO home() {
        return recipeService.getInitialRecipes();
    }

    @Operation(summary = "Returns recipe")
    @GetMapping("/{id}")
    @ResponseBody
    public RecipeResponseDTO get(@PathVariable Long id) {
        return conversionService.convert(recipeService.getRecipe(id), RecipeResponseDTO.class);
    }

    @Operation(summary = "Edits recipe")
    @SecurityRequirement(name = "token")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody RecipeRequestDTO request) {
        recipeService.editRecipe(id, conversionService.convert(request, Recipe.class));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deletes recipe")
    @SecurityRequirement(name = "token")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        recipeService.removeRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Returns recipe's comments")
    @GetMapping("/{id}/comments")
    @ResponseBody
    public PageResponseDTO<CommentResponseDTO> getComments(@PathVariable Long id, @RequestParam int size,
                                                           @RequestParam int page){
        return recipeService.searchComments(id, page, size);
    }

    @Operation(summary = "Adds comment to the recipe")
    @SecurityRequirement(name = "token")
    @PostMapping("/{id}/comments")
    @ResponseBody
    public CommentResponseDTO addComment(@PathVariable Long id, @RequestBody CommentRequestDTO request) {
        return conversionService.convert(recipeService.addComment(conversionService.convert(request, Comment.class), id),
                CommentResponseDTO.class);
    }

    @Operation(summary = "Edit comment of the recipe")
    @SecurityRequirement(name = "token")
    @PutMapping("/{recipeId}/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<?> editComment(@PathVariable Long recipeId, @PathVariable Long commentId,
                                         @RequestBody CommentRequestDTO request) {
        recipeService.editComment(conversionService.convert(request, Comment.class), recipeId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Removes comment from the recipe")
    @SecurityRequirement(name = "token")
    @DeleteMapping("/{recipeId}/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<?> removeComment(@PathVariable Long recipeId, @PathVariable Long commentId) {
        recipeService.removeComment(recipeId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Returns recipe's rating")
    @GetMapping("/{id}/rating")
    @ResponseBody
    public int getRating(@PathVariable Long id){
        return recipeService.getRating(id);
    }

    @Operation(summary = "Adds recipe to the \"favourites\" of currently logged in user")
    @SecurityRequirement(name = "token")
    @GetMapping("/{id}/toggleFavourites")
    public ResponseEntity<?> addToFavourites(@PathVariable Long id) {
        recipeService.toggleFavourites(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Adds recipe to the \"made it\" of currently logged in user")
    @SecurityRequirement(name = "token")
    @GetMapping("/{id}/toggleMadeIt")
    public ResponseEntity<?> addToMadeit(@PathVariable Long id) {
        recipeService.toggleMadeIt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/start")
    @ResponseBody
    public StartResponseDTO start() {
        return recipeService.start();
    }
}

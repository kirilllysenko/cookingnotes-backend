package com.kirill.cookingnotes.profile.controller;

import com.kirill.cookingnotes.profile.dto.MeUserResponseDTO;
import com.kirill.cookingnotes.profile.dto.ProfilePublicRequestDTO;
import com.kirill.cookingnotes.profile.dto.PublicProfileResponseDTO;
import com.kirill.cookingnotes.profile.service.UserService;
import com.kirill.cookingnotes.recipe.dto.PageResponseDTO;
import com.kirill.cookingnotes.recipe.dto.RecipePreviewResponseDTO;
import com.kirill.cookingnotes.recipe.service.RecipeService;
import com.kirill.cookingnotes.recipe.specification.RecipeSpecification;
import com.kirill.cookingnotes.security.AuthProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;
    private final ConversionService conversionService;

    @SecurityRequirement(name = "token")
    @Operation(summary = "Returns information about currently logged in user")
    @GetMapping("/me")
    @ResponseBody
    public MeUserResponseDTO me() {
        return conversionService.convert(userService.getAuthenticatedUser(), MeUserResponseDTO.class);
    }

    @Operation(summary = "Returns base information about user")
    @GetMapping("/{id}/common")
    @ResponseBody
    public PublicProfileResponseDTO getProfile(@PathVariable Long id) {
        return conversionService.convert(userService.getUser(id), PublicProfileResponseDTO.class);
    }

    @Operation(summary = "Returns favourites recipes of user")
    @GetMapping("/{id}/favourites")
    @ResponseBody
    public PageResponseDTO<RecipePreviewResponseDTO> getFavourites(@PathVariable Long id, @RequestParam String title,
                                                                   @RequestParam int category, @RequestParam int page, @RequestParam int size) {
        return recipeService.searchRecipes(title, page, size, category, RecipeSpecification.byFavouritesId(id));
    }

    @Operation(summary = "Returns \"made it\" recipes of user")
    @GetMapping("/{id}/madeit")
    @ResponseBody
    public PageResponseDTO<RecipePreviewResponseDTO> getMadeit(@PathVariable Long id, @RequestParam String title,
                                                    @RequestParam int category, @RequestParam int page, @RequestParam int size) {
        return recipeService.searchRecipes(title, page, size, category, RecipeSpecification.byMadeItId(id));
    }

    @Operation(summary = "Returns recipes written by user")
    @GetMapping("/{id}/personal")
    @ResponseBody
    public PageResponseDTO<RecipePreviewResponseDTO> getPersonalRecipes(@PathVariable Long id, @RequestParam String title,
                                                             @RequestParam int category, @RequestParam int page, @RequestParam int size) {
        return recipeService.searchRecipes(title, page, size, category, RecipeSpecification.byUserId(id));
    }

    @SecurityRequirement(name = "token")
    @Operation(summary = "Edits user information")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody ProfilePublicRequestDTO request) {
        userService.edit(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @SecurityRequirement(name = "token")
    @Operation(summary = "Returns true if account is local")
    @GetMapping("/{id}/local")
    @ResponseBody
    public boolean isLocal(@PathVariable Long id) {
        return userService.getUser(id).getProvider() == AuthProvider.local;
    }

    @SecurityRequirement(name = "token")
    @Operation(summary = "Deletes user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

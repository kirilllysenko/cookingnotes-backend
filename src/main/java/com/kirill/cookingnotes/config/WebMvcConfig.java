package com.kirill.cookingnotes.config;

import com.kirill.cookingnotes.profile.converter.UserToMeResponseConverter;
import com.kirill.cookingnotes.profile.converter.UserToResponseConverter;
import com.kirill.cookingnotes.recipe.converter.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final RequestToRecipeConverter requestToRecipeConverter;
    private final CommentToResponseConverter commentToResponseConverter;
    private final RecipeToResponseConverter recipeToResponseConverter;
    private final RequestToCommentConverter requestToCommentConverter;
    private final RecipesToPreviewConverter recipesToPreviewConverter;
    private final UserToMeResponseConverter userToMeResponseConverter;
    private final UserToResponseConverter userToResponseConverter;
    private final RecipesToCarouselConverter recipesToCarouselConverter;

    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(requestToRecipeConverter);
        registry.addConverter(commentToResponseConverter);
        registry.addConverter(recipeToResponseConverter);
        registry.addConverter(requestToCommentConverter);
        registry.addConverter(recipesToPreviewConverter);
        registry.addConverter(userToMeResponseConverter);
        registry.addConverter(userToResponseConverter);
        registry.addConverter(recipesToCarouselConverter);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        long MAX_AGE_SECS = 3600;
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
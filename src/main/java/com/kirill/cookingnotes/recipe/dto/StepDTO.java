package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class StepDTO {
    private  byte[] image;

    @NotBlank
    private  String text;
}

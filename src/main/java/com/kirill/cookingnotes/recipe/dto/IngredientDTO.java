package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class IngredientDTO {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private int unit;
}

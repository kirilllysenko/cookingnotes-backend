package com.kirill.cookingnotes.recipe.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDTO {
    private int id;
    private String name;
}

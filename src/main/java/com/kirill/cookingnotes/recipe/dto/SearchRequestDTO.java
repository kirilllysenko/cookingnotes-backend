package com.kirill.cookingnotes.recipe.dto;

import lombok.Data;

@Data
public class SearchRequestDTO {
    private String title;
    private int start;
    private int category;
}

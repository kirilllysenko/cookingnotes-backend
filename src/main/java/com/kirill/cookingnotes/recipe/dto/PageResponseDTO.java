package com.kirill.cookingnotes.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponseDTO<T> {
   private boolean hasMore;
   private List<T> content;
}

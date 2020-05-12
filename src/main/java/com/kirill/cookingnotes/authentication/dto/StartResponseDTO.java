package com.kirill.cookingnotes.authentication.dto;

import com.kirill.cookingnotes.recipe.dto.CategoryResponseDTO;
import com.kirill.cookingnotes.recipe.dto.UnitResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StartResponseDTO {
    private List<CategoryResponseDTO> categories;
    private List<UnitResponseDTO> units;
}

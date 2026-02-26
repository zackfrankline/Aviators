package com.aviator.content_servive.mapper;

import com.aviator.content_servive.dto.CategoryRequestDTO;
import com.aviator.content_servive.dto.CategoryResponseDTO;
import com.aviator.content_servive.model.Category;

public class CategoryMapper {
    public static CategoryResponseDTO toDTO(Category category){
        return CategoryResponseDTO.builder()
                .Id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt().toString())
                .build();
    }

    public static Category toModel(CategoryRequestDTO categoryRequestDTO){
        return Category.builder()
                .name(categoryRequestDTO.getName())
                .slug(categoryRequestDTO.getSlug())
                .description(categoryRequestDTO.getDescription())
                .build();
    }

    public static void updateDtoToModel(CategoryRequestDTO categoryRequestDTO, Category category){
        category.setSlug(categoryRequestDTO.getSlug());
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
    }
}

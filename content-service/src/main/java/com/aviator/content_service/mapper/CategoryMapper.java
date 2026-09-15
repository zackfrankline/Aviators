package com.aviator.content_service.mapper;

import java.util.UUID;


import com.aviator.content_service.dto.CategoryRequestDTO;
import com.aviator.content_service.dto.CategoryResponseDTO;
import com.aviator.content_service.model.Category;

public class CategoryMapper {

    private CategoryMapper(){
        // this Mapper class for Category cannot be instantiated
    }

    public static CategoryResponseDTO toDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt().toString())
                .build();
    }

    public static Category toModel(CategoryRequestDTO categoryRequestDTO, String slug){
        return Category.builder()
                .name(categoryRequestDTO.getName())
                .slug(slug)
                .description(categoryRequestDTO.getDescription())
                .createdBy(UUID.fromString(categoryRequestDTO.getCreatedBy()))
                .build();
    }

    public static void updateDtoToModel(CategoryRequestDTO categoryRequestDTO, Category category){
        category.setSlug(categoryRequestDTO.getSlug());
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
    }
}

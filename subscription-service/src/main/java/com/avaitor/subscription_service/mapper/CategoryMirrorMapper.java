package com.avaitor.subscription_service.mapper;

import com.avaitor.subscription_service.dto.CategoryMirrorDto;
import com.avaitor.subscription_service.model.CategoryMirror;

public class CategoryMirrorMapper {
    public static CategoryMirrorDto toDTO(CategoryMirror categoryMirror){
        return CategoryMirrorDto.builder()
                .Id(categoryMirror.getId().toString())
                .name(categoryMirror.getName())
                .slug(categoryMirror.getSlug())
                .createdAt(categoryMirror.getCreatedAt().toString())
                .build();
    }
}

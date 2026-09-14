package com.avaitor.subscription_service.mapper;

import com.avaitor.subscription_service.dto.CategoryMirrorDto;
import com.avaitor.subscription_service.model.CategoryMirror;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryMirrorMapper {

    private CategoryMirrorMapper(){
        //cannot instantiate CategoryMirrorMapper
    }

    public static CategoryMirrorDto toDTO(CategoryMirror categoryMirror){
        return CategoryMirrorDto.builder()
                .id(categoryMirror.getId().toString())
                .name(categoryMirror.getName())
                .slug(categoryMirror.getSlug())
                .createdAt(categoryMirror.getCreatedAt().toString())
                .build();
    }
    public static CategoryMirror toModel(CategoryMirrorDto categoryMirrorDto){
        return CategoryMirror.builder()
                .id(UUID.fromString(categoryMirrorDto.getId()))
                .name(categoryMirrorDto.getName())
                .slug(categoryMirrorDto.getSlug())
                .createdAt(LocalDateTime.parse(categoryMirrorDto.getCreatedAt()))
                .build();
    }
}

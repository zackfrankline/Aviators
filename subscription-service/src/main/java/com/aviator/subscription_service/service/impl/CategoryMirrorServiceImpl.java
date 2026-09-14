package com.aviator.subscription_service.service.impl;

import com.aviator.subscription_service.dto.CategoryEvent;
import com.aviator.subscription_service.dto.CategoryMirrorDto;
import com.aviator.subscription_service.mapper.CategoryMirrorMapper;
import com.aviator.subscription_service.model.CategoryMirror;
import com.aviator.subscription_service.repository.CategoryMirrorRepository;
import com.aviator.subscription_service.service.CategoryMirrorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryMirrorServiceImpl implements CategoryMirrorService {

    private final CategoryMirrorRepository categoryMirrorRepository;

    public CategoryMirrorServiceImpl(CategoryMirrorRepository categoryMirrorRepository){
        this.categoryMirrorRepository = categoryMirrorRepository;
    }


    @Transactional
    @Override
    public void upsertCategory(CategoryEvent categoryEvent) {
        CategoryMirror categoryMirror = CategoryMirror.builder()
                .id(UUID.fromString(categoryEvent.getId()))
                .name(categoryEvent.getTitle())
                .slug(categoryEvent.getSlug())
                .build();
        categoryMirrorRepository.save(categoryMirror);
    }

    @Transactional
    @Override
    public void deleteCategory(CategoryEvent categoryEvent) {
        categoryMirrorRepository.deleteById(UUID.fromString(categoryEvent.getId()));
    }

    @Override
    public List<CategoryMirrorDto> getAllCategories() {
        return categoryMirrorRepository.findAll().stream()
                .map(CategoryMirrorMapper::toDTO).toList();
    }
}

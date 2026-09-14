package com.aviator.subscription_service.service;

import com.aviator.subscription_service.dto.CategoryEvent;
import com.aviator.subscription_service.dto.CategoryMirrorDto;

import java.util.List;

public interface CategoryMirrorService {
    public void upsertCategory(CategoryEvent categoryEvent);
    public void deleteCategory(CategoryEvent categoryEvent);
    public List<CategoryMirrorDto> getAllCategories();
}

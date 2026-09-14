package com.aviator.content_service.service;

import com.aviator.content_service.dto.CategoryRequestDTO;
import com.aviator.content_service.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDTO> getAllCategories();
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO);
    public void deleteCategory(String categoryId);
}

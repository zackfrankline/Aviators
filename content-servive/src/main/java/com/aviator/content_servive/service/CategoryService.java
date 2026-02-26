package com.aviator.content_servive.service;

import com.aviator.content_servive.dto.CategoryRequestDTO;
import com.aviator.content_servive.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDTO> getAllCategories();
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    public CategoryResponseDTO updateCategory(String slug, CategoryRequestDTO categoryRequestDTO);
    public void deleteCategory(String slug);
}

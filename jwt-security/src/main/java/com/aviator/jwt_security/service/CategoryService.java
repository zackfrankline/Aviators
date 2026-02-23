package com.aviator.jwt_security.service;

import com.aviator.jwt_security.dto.CategoryRequestDTO;
import com.aviator.jwt_security.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDTO> getAllCategories();
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    public CategoryResponseDTO updateCategory(String slug, CategoryRequestDTO categoryRequestDTO);
    public void deleteCategory(String slug);
}

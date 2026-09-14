package com.aviator.content_service.controller;


import com.aviator.content_service.dto.CategoryRequestDTO;
import com.aviator.content_service.dto.CategoryResponseDTO;
import com.aviator.content_service.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categoryResponseDTOList = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categoryResponseDTOList);
    }


    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDTO> updateCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(categoryRequestDTO);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body("Category Deleted.");
    }
}

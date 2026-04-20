package com.aviator.content_servive.controller;


import com.aviator.content_servive.dto.CategoryRequestDTO;
import com.aviator.content_servive.dto.CategoryResponseDTO;
import com.aviator.content_servive.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
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

    @DeleteMapping
    public ResponseEntity<String> deleteCategory(String Id){
        categoryService.deleteCategory(Id);
        return ResponseEntity.ok().body("Category Deleted.");
    }
}

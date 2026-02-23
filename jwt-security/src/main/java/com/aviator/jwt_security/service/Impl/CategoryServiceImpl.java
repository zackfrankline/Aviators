package com.aviator.jwt_security.service.Impl;

import com.aviator.jwt_security.dto.CategoryRequestDTO;
import com.aviator.jwt_security.dto.CategoryResponseDTO;
import com.aviator.jwt_security.mapper.CategoryMapper;
import com.aviator.jwt_security.model.Article;
import com.aviator.jwt_security.model.Category;
import com.aviator.jwt_security.repository.ArticleRepository;
import com.aviator.jwt_security.repository.CategoryRepository;
import com.aviator.jwt_security.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository){
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * @return CategoryResponseDTO
     */
    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::toDTO).toList();
    }

    /**
     * @return
     */
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = CategoryMapper.toModel(categoryRequestDTO);
        try{
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return CategoryMapper.toDTO(category);
    }

    /**
     * @return
     */
    @Override
    public CategoryResponseDTO updateCategory(String slug, CategoryRequestDTO categoryRequestDTO) throws IllegalArgumentException{
        Category category = categoryRepository.findBySlug(slug);
        if(category == null)
            throw new IllegalArgumentException("No Category found.");
        CategoryMapper.updateDtoToModel(categoryRequestDTO, category);
        try{
            categoryRepository.save(category);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return CategoryMapper.toDTO(category);
    }

    /**
     *
     */
    @Override
    public void deleteCategory(String slug) throws IllegalArgumentException{
        Category category = categoryRepository.findBySlug(slug);
        if(category == null)
            throw new IllegalArgumentException("No Category found.");
        int articleUpdated = articleRepository.updateCategoryFieldToNull(category.getId().toString());
        return;
    }
}

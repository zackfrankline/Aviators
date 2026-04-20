package com.aviator.content_servive.service.Impl;

import com.aviator.content_servive.dto.CategoryRequestDTO;
import com.aviator.content_servive.dto.CategoryResponseDTO;
import com.aviator.content_servive.exception.DuplicateResourceException;
import com.aviator.content_servive.exception.ResourceNotFoundException;
import com.aviator.content_servive.mapper.CategoryMapper;
import com.aviator.content_servive.model.Category;
import com.aviator.content_servive.repository.ArticleRepository;
import com.aviator.content_servive.repository.CategoryRepository;
import com.aviator.content_servive.service.CategoryService;

import com.aviator.content_servive.utility.SlugUtility;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        String formattedSlug = SlugUtility.generateSlug(categoryRequestDTO.getName());

        //validate
        validateCategorySlug(formattedSlug,null);

        Category category = CategoryMapper.toModel(categoryRequestDTO, formattedSlug);
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
    @Transactional
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO) throws IllegalArgumentException{

        String categoryId = categoryRequestDTO.getId();
        Category category = categoryRepository.findById(UUID.fromString(categoryId)).orElseThrow(
                () ->  new ResourceNotFoundException("Category not found with ID")
        );
        String formatedSlug = SlugUtility.generateSlug(categoryRequestDTO.getName());
        categoryRequestDTO.setSlug(formatedSlug);

        //validate slug update request only if name is changed
        if(!category.getSlug().equals(formatedSlug)){
            validateCategorySlug(formatedSlug, categoryId);
        }

        categoryRequestDTO.setSlug(formatedSlug);
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
    @Transactional
    public void deleteCategory(String Id){
        UUID categoryId = UUID.fromString(Id);
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("No Category found by Id")
        );
        try{
            articleRepository.updateCategoryFieldToNull(category.getId().toString());
            categoryRepository.deleteById(UUID.fromString(Id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return;
    }

    private void validateCategorySlug(String slug, String categoryId){
        //validate existing slug with new updated slug
        if(slug != null && categoryRepository.existsBySlugAndIdNot(slug, UUID.fromString(categoryId))){
            throw new DuplicateResourceException("Category Name Already Exists.");
        }
    }
}

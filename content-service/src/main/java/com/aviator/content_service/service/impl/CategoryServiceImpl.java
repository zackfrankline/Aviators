package com.aviator.content_service.service.impl;

import com.aviator.content_service.controller.CategoryController;
import com.aviator.content_service.dto.CategoryEvent;
import com.aviator.content_service.dto.CategoryRequestDTO;
import com.aviator.content_service.dto.CategoryResponseDTO;
import com.aviator.content_service.exception.DuplicateResourceException;
import com.aviator.content_service.exception.ResourceNotFoundException;
import com.aviator.content_service.mapper.CategoryMapper;
import com.aviator.content_service.model.Category;
import com.aviator.content_service.repository.ArticleRepository;
import com.aviator.content_service.repository.CategoryRepository;
import com.aviator.content_service.security.SecurityUtility;
import com.aviator.content_service.service.CategoryService;

import com.aviator.content_service.service.EventPublisher;
import com.aviator.content_service.utility.SlugUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final EventPublisher eventPublisher;
    private final SecurityUtility securityUtility;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository, EventPublisher eventPublisher
        , SecurityUtility securityUtility, CategoryController categoryController
    ){
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.eventPublisher = eventPublisher;
        this.securityUtility = securityUtility;

    }

    /**
     * returns List of Category Request DTO
     * 
     * @return CategoryResponseDTO
     */
    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::toDTO).toList();
    }

    /**
     * Returns newly inserted Category Response DTO
     * 
     * @param CategoryRequestDTO 
     * @return Categery Response DTO
     */
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        String formattedSlug = SlugUtility.generateSlug(categoryRequestDTO.getName());
        UUID userId = securityUtility.getCurrentUserId();
        //validate
        validateCategorySlug(formattedSlug,null);
        if(userId == null){
            throw new IllegalArgumentException("No User is logged In");
        }
        categoryRequestDTO.setCreatedBy(userId.toString());
        Category category = CategoryMapper.toModel(categoryRequestDTO, formattedSlug);
        try{
            categoryRepository.save(category);
            CategoryEvent categoryEvent = CategoryEvent.builder()
                    .id(category.getId().toString())
                    .slug(category.getSlug())
                    .title(category.getName())
                    .eventType("UPSERT")
                    .build();
            eventPublisher.sendEventMessage(categoryEvent);

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return CategoryMapper.toDTO(category);
    }


    /**
     * Updates Category By the Owner of the Category
     * 
     * @param CategoryRequestDTO
     * @return CategoryResponseDTO
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

        UUID userId = securityUtility.getCurrentUserId();

        if(category.getCreatedBy() != userId){
            throw new IllegalArgumentException("Other Users cannot update Category Owned by Other User.");
        }

        categoryRequestDTO.setSlug(formatedSlug);
        CategoryMapper.updateDtoToModel(categoryRequestDTO, category);
        try{
            categoryRepository.save(category);
            CategoryEvent categoryEvent = CategoryEvent.builder()
                    .id(category.getId().toString())
                    .slug(category.getSlug())
                    .title(category.getName())
                    .eventType("UPSERT")
                    .build();
            eventPublisher.sendEventMessage(categoryEvent);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return CategoryMapper.toDTO(category);
    }

    /**
     * Deletes Category Record by the Category Owner
     * @param id : String
     */
    @Override
    @Transactional
    public void deleteCategory(String id){
        UUID categoryId = UUID.fromString(id);
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("No Category found by Id")
        );
        UUID userId = securityUtility.getCurrentUserId();

        if(category.getCreatedBy() != userId){
            throw new IllegalArgumentException("Other Users cannot update Category Owned by Other User.");
        }
        try{
            articleRepository.updateCategoryFieldToNull(category.getId());
            CategoryEvent categoryEvent = CategoryEvent.builder()
                    .id(category.getId().toString())
                    .slug(category.getSlug())
                    .title(category.getName())
                    .eventType("DELETE")
                    .build();
            categoryRepository.deleteById(UUID.fromString(id));
            eventPublisher.sendEventMessage(categoryEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateCategorySlug(String slug, String categoryId){
        UUID categoryUUID = categoryId == null ? null: UUID.fromString(categoryId);
        //validate existing slug with new updated slug
        if(slug != null && categoryRepository.existsBySlugAndIdNot(slug, categoryUUID)){
            throw new DuplicateResourceException("Category Name Already Exists.");
        }
    }
}

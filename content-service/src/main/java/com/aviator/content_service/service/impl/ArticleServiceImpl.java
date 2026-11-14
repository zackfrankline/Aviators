package com.aviator.content_service.service.impl;

import com.aviator.content_service.dto.ArticleRequestDTO;
import com.aviator.content_service.dto.ArticleResponseDTO;
import com.aviator.content_service.exception.DuplicateResourceException;
import com.aviator.content_service.exception.ResourceNotFoundException;
import com.aviator.content_service.mapper.ArticleMapper;
import com.aviator.content_service.model.Article;
import com.aviator.content_service.repository.ArticleRepository;
import com.aviator.content_service.repository.CategoryRepository;
import com.aviator.content_service.service.ArticleService;
import com.aviator.content_service.security.SecurityUtility;
import com.aviator.content_service.utility.SlugUtility;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    private final SecurityUtility securityUtility;


    public ArticleServiceImpl(ArticleRepository articleRepository, SecurityUtility securityUtility, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.securityUtility = securityUtility;
    }

    /**    (non-Javadoc)
     * Returns List of Article Response DTO of Published Article for Audience and All for Admin.
     * 
     * @return List<ArticleRequestDTO>
     */
    @Override
    public List<ArticleResponseDTO> getAllArticles() {

        List<Article> articles;
        if(securityUtility.isCurrentUserAdmin()){
            articles = articleRepository.findAll();
        }
        else{
            articles = articleRepository.findByStatus("Published");
        }
        return articles.stream()
                .map(ArticleMapper::toDTO).toList();
    }

    /**    (non-Javadoc)
     * Creates Article using articleId by the current logged in Admin User.
     * 
     * @param ArticleRequestDTO
     * @return ArticleRequestDTO
     */
    @Override
    @Transactional
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO) {
        String articleTitle = articleRequestDTO.getTitle();
        String formattedSlug = SlugUtility.generateSlug(articleTitle);

        validateArticleSlug(formattedSlug, null);

        //CHECK IF THE CATEGORY ID is VALID
        if(!articleRequestDTO.getCategoryId().isBlank()){
            categoryRepository.findById(UUID.fromString(articleRequestDTO.getCategoryId())).orElseThrow(
                    () -> new ResourceNotFoundException("No Category Found with the Selected ID")
            );
        }

        UUID userId = securityUtility.getCurrentUserId();
        if(userId == null){
            throw new ResourceNotFoundException("User Not Found.");
        }

        Article article = ArticleMapper.toModel(articleRequestDTO, formattedSlug, userId);

        try{
            articleRepository.save(article);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return ArticleMapper.toDTO(article);
    }

    /**
     * Updates Article using articleId by the current logged in User.
     * 
     * @param ArticleRequestDTO
     * @return ArticleRequestDTO
     */
    @Override
    @Transactional
    public ArticleResponseDTO updateArticle(ArticleRequestDTO articleRequestDTO) {
        String articleTitle = articleRequestDTO.getTitle();
        String formattedSlug = SlugUtility.generateSlug(articleTitle);
        UUID articleId = UUID.fromString(articleRequestDTO.getId());

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ResourceNotFoundException("No Article found for given Id")
        );

        if(!article.getSlug().equals(formattedSlug)){
            validateArticleSlug(formattedSlug, articleId);
            articleRequestDTO.setSlug(formattedSlug);
        }

        UUID loggedInUserId = securityUtility.getCurrentUserId();
        if(loggedInUserId != article.getAuthorId()){
            throw new IllegalArgumentException("Only Article Author can update Article.");
        }

        ArticleMapper.updateDtoToModel(articleRequestDTO, article);

        try{
            articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ArticleMapper.toDTO(article);
    }

    /**
     * Deletes Article using articleId by the current logged in User.
     * 
     * @param articleRequestDTO
     */
    @Override
    @Transactional
    public void deleteArticle(ArticleRequestDTO articleRequestDTO) {
        UUID articleId = UUID.fromString(articleRequestDTO.getId());
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ResourceNotFoundException("No Article Found")
        );
        UUID loggedInUserId = securityUtility.getCurrentUserId();
        if(loggedInUserId != article.getAuthorId()){
            throw new IllegalArgumentException("Only Article Author can delete Article.");
        }
        try{
            articleRepository.delete(article);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    private void validateArticleSlug(String slug, UUID id){
        if(articleRepository.existsBySlugAndIdNot(slug, id)){
            throw new DuplicateResourceException("Article Title should be unique");
        }
    }

}

package com.aviator.content_servive.service.Impl;

import com.aviator.content_servive.dto.ArticleRequestDTO;
import com.aviator.content_servive.dto.ArticleResponseDTO;
import com.aviator.content_servive.exception.DuplicateResourceException;
import com.aviator.content_servive.exception.ResourceNotFoundException;
import com.aviator.content_servive.mapper.ArticleMapper;
import com.aviator.content_servive.model.Article;
import com.aviator.content_servive.repository.ArticleRepository;
import com.aviator.content_servive.service.ArticleService;
import com.aviator.content_servive.security.SecurityUtility;
import com.aviator.content_servive.utility.SlugUtility;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    private final SecurityUtility securityUtility;


    public ArticleServiceImpl(ArticleRepository articleRepository, SecurityUtility securityUtility){
        this.articleRepository = articleRepository;

        this.securityUtility = securityUtility;
    }

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


    @Override
    @Transactional
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO) {
        String articleTitle = articleRequestDTO.getTitle();
        String formattedSlug = SlugUtility.generateSlug(articleTitle);

        validateArticleSlug(formattedSlug, null);

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
     * @param articleRequestDTO
     * @return
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

        ArticleMapper.updateDtoToModel(articleRequestDTO, article);

        try{
            articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ArticleMapper.toDTO(article);
    }

    /**
     * @param articleRequestDTO
     */
    @Override
    @Transactional
    public void deleteArticle(ArticleRequestDTO articleRequestDTO) {
        UUID articleId = UUID.fromString(articleRequestDTO.getId());
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ResourceNotFoundException("No Article Found")
        );
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

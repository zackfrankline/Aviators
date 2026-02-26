package com.aviator.content_servive.service.Impl;

import com.aviator.content_servive.dto.ArticleRequestDTO;
import com.aviator.content_servive.dto.ArticleResponseDTO;
import com.aviator.content_servive.mapper.ArticleMapper;
import com.aviator.content_servive.model.Article;
import com.aviator.content_servive.repository.ArticleRepository;
import com.aviator.content_servive.service.ArticleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;

    }

    @Override
    public List<ArticleResponseDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map( ArticleMapper::toDTO).toList();
    }


    @Override
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO) {
        //check if article title and slug exits
        if(articleRepository.existsByTitleIgnoreCaseOrSlug(articleRequestDTO.getTitle(), articleRequestDTO.getSlug())){
            throw new IllegalArgumentException("Title and Slug should be unique");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getPrincipal();
        System.out.print(userId);
        Article article = ArticleMapper.toModel(articleRequestDTO);
        article.setAuthorId(userId);

        //check for status is published then default today date for published at
        if(articleRequestDTO.getStatus().equals("Published")){
            article.setPublishedAt(LocalDateTime.now());
        }
        try{
            articleRepository.save(article);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return ArticleMapper.toDTO(article);
    }
}

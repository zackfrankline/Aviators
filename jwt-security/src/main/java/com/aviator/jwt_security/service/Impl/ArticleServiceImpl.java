package com.aviator.jwt_security.service.Impl;

import com.aviator.jwt_security.dto.ArticleRequestDTO;
import com.aviator.jwt_security.dto.ArticleResponseDTO;
import com.aviator.jwt_security.mapper.ArticleMapper;
import com.aviator.jwt_security.model.Article;
import com.aviator.jwt_security.model.User;
import com.aviator.jwt_security.repository.ArticleRepository;
import com.aviator.jwt_security.repository.UserRepository;
import com.aviator.jwt_security.service.ArticleService;
import com.aviator.jwt_security.service.AuthService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthService authService;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              AuthService authService){
        this.articleRepository = articleRepository;
        this.authService = authService;
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

        User currentUser = authService.getCurrentLoggedInUser();
        Article article = ArticleMapper.toModel(articleRequestDTO);
        article.setAuthorId(currentUser.getId());

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

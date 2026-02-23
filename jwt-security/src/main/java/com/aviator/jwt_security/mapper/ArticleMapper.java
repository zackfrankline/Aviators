package com.aviator.jwt_security.mapper;


import com.aviator.jwt_security.dto.ArticleRequestDTO;
import com.aviator.jwt_security.dto.ArticleResponseDTO;
import com.aviator.jwt_security.model.Article;

import java.time.LocalDateTime;
import java.util.UUID;

public class ArticleMapper {
    public static ArticleResponseDTO toDTO(Article article){
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .slug(article.getSlug())
                .summary(article.getSummary())
                .content(article.getContent())
                .status(article.getStatus())
                .categoryId(article.getCategoryId())
                .authorId(article.getAuthorId())
                .bannerUrl(article.getBannerUrl())
                .youtubeLink(article.getYoutubeLink())
                .pdfUrl(article.getPdfUrl())
                .publishedAt(article.getPublishedAt())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    public static Article toModel(ArticleRequestDTO articleRequestDTO){
        return Article.builder()
                .title(articleRequestDTO.getTitle())
                .slug(articleRequestDTO.getSlug())
                .summary(articleRequestDTO.getSummary())
                .content(articleRequestDTO.getContent())
                .status(articleRequestDTO.getStatus())
                .categoryId(articleRequestDTO.getCategoryId())
                .authorId(articleRequestDTO.getAuthorId())
                .bannerUrl(articleRequestDTO.getBannerUrl())
                .youtubeLink(articleRequestDTO.getYoutubeLink())
                .pdfUrl(articleRequestDTO.getPdfUrl())
                .build();
    }
}

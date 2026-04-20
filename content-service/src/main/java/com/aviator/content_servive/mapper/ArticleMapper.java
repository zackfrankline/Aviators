package com.aviator.content_servive.mapper;


import com.aviator.content_servive.dto.ArticleRequestDTO;
import com.aviator.content_servive.dto.ArticleResponseDTO;
import com.aviator.content_servive.model.Article;

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

    public static Article toModel(ArticleRequestDTO articleRequestDTO, String slug, UUID authorId){

        String categoryIdStr = articleRequestDTO.getCategoryId();
        UUID categoryId = categoryIdStr.isBlank() ? null : UUID.fromString(categoryIdStr);
        LocalDateTime publishedDate = LocalDateTime.now();

        //check for status is published then default today date for published at
        if(!articleRequestDTO.getStatus().equals("Published")){
            publishedDate = null;
        }

        return Article.builder()
                .title(articleRequestDTO.getTitle())
                .summary(articleRequestDTO.getSummary())
                .content(articleRequestDTO.getContent())
                .status(articleRequestDTO.getStatus())
                .slug(slug)
                .authorId(authorId)
                .categoryId(categoryId)
                .publishedAt(publishedDate)
                .bannerUrl(articleRequestDTO.getBannerUrl())
                .youtubeLink(articleRequestDTO.getYoutubeLink())
                .pdfUrl(articleRequestDTO.getPdfUrl())
                .build();
    }

    public static void updateDtoToModel(ArticleRequestDTO articleRequestDTO, Article article){
        String categoryIdStr = articleRequestDTO.getCategoryId();
        UUID categoryId = categoryIdStr.isBlank() ? null : UUID.fromString(categoryIdStr);

        String oldStatus = article.getStatus();

        article.setTitle(articleRequestDTO.getTitle());
        article.setSummary(articleRequestDTO.getSummary());
        article.setContent(articleRequestDTO.getContent());
        article.setSlug(articleRequestDTO.getSlug());
        article.setStatus(articleRequestDTO.getStatus());
        article.setCategoryId(categoryId);
        article.setBannerUrl(articleRequestDTO.getBannerUrl());
        article.setYoutubeLink(articleRequestDTO.getYoutubeLink());
        article.setPdfUrl(articleRequestDTO.getPdfUrl());

        if(!oldStatus.equals(articleRequestDTO.getStatus()) && articleRequestDTO.getStatus().equals("Published")){
            article.setPublishedAt(LocalDateTime.now());
        }
    }
}

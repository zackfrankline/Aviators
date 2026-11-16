package com.aviator.content_service.mapper;


import com.aviator.content_service.dto.ArticleRequestDTO;
import com.aviator.content_service.dto.ArticleResponseDTO;
import com.aviator.content_service.model.Article;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ArticleMapper {

    // Thread-safe static ObjectMapper with Java 8 Date/Time modules auto-registered
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .findAndAddModules()
            .build();

    // Reusable TypeReference avoids recreating anonymous inner class on every call
    private static final TypeReference<Map<String, Object>> MAP_TYPE_REF = new TypeReference<>() {};


    private ArticleMapper(){
        //  THIS MAPPER CANNOT BE INSTANTIATED
    }

    private static String serializeContent(Map<String, Object> content) {
        if (content == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(content);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize article content to JSON string", e);
        }
    }

    private static Map<String, Object> deserializeContent(String contentJson) {
        if (contentJson == null || contentJson.isBlank()) {
            return new HashMap<>();
        }
        try {
            return OBJECT_MAPPER.readValue(contentJson, MAP_TYPE_REF);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format in article content", e);
        }
    }


    public static ArticleResponseDTO toDTO(Article article){
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .slug(article.getSlug())
                .summary(article.getSummary())
                .content(serializeContent(article.getContent()))
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
        LocalDateTime publishedDate = LocalDateTime.now(ZoneId.systemDefault());

        //check for status is published then default today date for published at
        if(!articleRequestDTO.getStatus().equals("Published")){
            publishedDate = null;
        }

        return Article.builder()
                .title(articleRequestDTO.getTitle())
                .summary(articleRequestDTO.getSummary())
                .content(deserializeContent(articleRequestDTO.getContent()))
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
        article.setContent(deserializeContent(articleRequestDTO.getContent()));
        article.setSlug(articleRequestDTO.getSlug());
        article.setStatus(articleRequestDTO.getStatus());
        article.setCategoryId(categoryId);
        article.setBannerUrl(articleRequestDTO.getBannerUrl());
        article.setYoutubeLink(articleRequestDTO.getYoutubeLink());
        article.setPdfUrl(articleRequestDTO.getPdfUrl());

        if(!oldStatus.equals(articleRequestDTO.getStatus()) && articleRequestDTO.getStatus().equals("Published")){
            article.setPublishedAt(LocalDateTime.now(ZoneId.systemDefault()));
        }
    }
}

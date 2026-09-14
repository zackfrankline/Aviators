package com.aviator.content_service.service;

import com.aviator.content_service.dto.ArticleRequestDTO;
import com.aviator.content_service.dto.ArticleResponseDTO;


import java.util.List;

public interface ArticleService {
    public List<ArticleResponseDTO> getAllArticles();
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO);
    public ArticleResponseDTO updateArticle(ArticleRequestDTO articleRequestDTO);
    public void deleteArticle(ArticleRequestDTO articleRequestDTO);
}

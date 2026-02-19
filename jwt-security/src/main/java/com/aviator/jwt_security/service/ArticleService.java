package com.aviator.jwt_security.service;

import com.aviator.jwt_security.dto.ArticleRequestDTO;
import com.aviator.jwt_security.dto.ArticleResponseDTO;

import java.util.List;

public interface ArticleService {
    public List<ArticleResponseDTO> getAllArticles();
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO);
}

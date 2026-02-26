package com.aviator.content_servive.service;

import com.aviator.content_servive.dto.ArticleRequestDTO;
import com.aviator.content_servive.dto.ArticleResponseDTO;


import java.util.List;

public interface ArticleService {
    public List<ArticleResponseDTO> getAllArticles();
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO);
}

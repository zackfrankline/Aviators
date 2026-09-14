package com.aviator.content_service.controller;

import com.aviator.content_service.dto.ArticleRequestDTO;
import com.aviator.content_service.dto.ArticleResponseDTO;
import com.aviator.content_service.dto.validation.UpdateArticleValidation;
import com.aviator.content_service.service.ArticleService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content/articles")
public class ArticleController {

    private final ArticleService articleService;

    ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles(){
        List<ArticleResponseDTO> articleResponseDTOList =  articleService.getAllArticles();
        return ResponseEntity.ok().body(articleResponseDTOList);
    }



    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(@Validated({Default.class}) @RequestBody ArticleRequestDTO articleRequestDTO){
        ArticleResponseDTO articleResponseDTO = articleService.createArticle(articleRequestDTO);
        return ResponseEntity.ok().body(articleResponseDTO);
    }

    @PutMapping
    public ResponseEntity<ArticleResponseDTO> updateArticle(@Validated({Default.class, UpdateArticleValidation.class}) @RequestBody ArticleRequestDTO articleRequestDTO){
        ArticleResponseDTO articleResponseDTO = articleService.updateArticle(articleRequestDTO);
        return ResponseEntity.ok().body(articleResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteArticle(@Validated({Default.class, UpdateArticleValidation.class}) @RequestBody ArticleRequestDTO articleRequestDTO){
        articleService.deleteArticle(articleRequestDTO);
        return ResponseEntity.ok().body("Article Deleted.");
    }

}

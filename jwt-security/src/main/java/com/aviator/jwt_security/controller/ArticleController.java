package com.aviator.jwt_security.controller;

import com.aviator.jwt_security.dto.ArticleRequestDTO;
import com.aviator.jwt_security.dto.ArticleResponseDTO;
import com.aviator.jwt_security.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
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

}

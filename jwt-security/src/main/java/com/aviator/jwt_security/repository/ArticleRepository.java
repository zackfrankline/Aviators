package com.aviator.jwt_security.repository;

import com.aviator.jwt_security.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    boolean existsByTitleIgnoreCaseOrSlug(String title, String slug);

    @Override
    List<Article> findAll();
}

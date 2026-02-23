package com.aviator.jwt_security.repository;

import com.aviator.jwt_security.model.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    boolean existsByTitleIgnoreCaseOrSlug(String title, String slug);

    @Override
    List<Article> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Article a SET a.categoryId = NULL WHERE a.categoryId = :categoryId")
    int updateCategoryFieldToNull(@Param("categoryId") String categoryId);
}

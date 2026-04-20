package com.aviator.content_servive.repository;

import com.aviator.content_servive.model.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Override
    List<Article> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Article a SET a.categoryId = NULL WHERE a.categoryId = :categoryId")
    void updateCategoryFieldToNull(@Param("categoryId") String categoryId);

    boolean existsBySlugAndIdNot(String slug, UUID Id);

    List<Article> findByStatus(String status);

}

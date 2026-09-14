package com.aviator.content_servive.repository;

import com.aviator.content_servive.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findBySlug(String slug);

    @Override
    Optional<Category> findById(UUID uuid);

    @Override
    List<Category> findAll();

    boolean existsBySlugAndIdNot(String slug, UUID Id);

    @Override
    void deleteById(UUID uuid);
}

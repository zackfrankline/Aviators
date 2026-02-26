package com.aviator.content_servive.repository;

import com.aviator.content_servive.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findBySlug(String slug);


    @Override
    List<Category> findAll();



}

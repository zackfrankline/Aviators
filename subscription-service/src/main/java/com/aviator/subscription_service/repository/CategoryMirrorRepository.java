package com.aviator.subscription_service.repository;

import com.aviator.subscription_service.model.CategoryMirror;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface CategoryMirrorRepository extends JpaRepository<CategoryMirror, UUID> {
    @Override
    List<CategoryMirror> findAll();
}

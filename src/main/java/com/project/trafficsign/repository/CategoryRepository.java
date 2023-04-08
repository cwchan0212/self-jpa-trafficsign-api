package com.project.trafficsign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.trafficsign.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}

package com.project.roadsign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.roadsign.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}

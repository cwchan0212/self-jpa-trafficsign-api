package com.project.roadsign.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.roadsign.entity.Category;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id);
    Page<Category> getCategoriesPageable(int pageNo, int pageSize, String column, String order);
}

package com.project.trafficsign.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.trafficsign.entity.Category;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id);
    Page<Category> getCategoriesPageable(int pageNo, int pageSize, String column, String order);
}

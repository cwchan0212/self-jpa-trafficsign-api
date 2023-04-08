package com.project.trafficsign.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.trafficsign.entity.Category;
import com.project.trafficsign.exception.CategoryNotFoundException;
import com.project.trafficsign.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImp implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return unwrapCategory(category, id);
    }


    @Override
    public Category saveCategory(Category category) {     
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    static Category unwrapCategory(Optional<Category> category, Long id)  {
        if (category.isPresent()) return category.get();
        else throw new CategoryNotFoundException(id);
    }

    public Long findMaxId() {
        Long maxId = 0L;
        for (Category category : categoryRepository.findAll()) {
            if (category.getId() > maxId) {
                maxId = category.getId();
            }
        }
        return maxId;
    }

    @Override
    public Page<Category> getCategoriesPageable(int pageNo, int pageSize, String column, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(column).ascending() : Sort.by(column).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return categoryRepository.findAll(pageable);
    }



    // public Long addCategory(String text, String description) {
    //     Long categoryId = categoryRepository.findMaxId() + 1L;
    //     Category category = new Category(categoryId, text, description, null);
    //     categoryRepository.add(category);
    //     return categoryId;
    // }

    // public void saveCategory(Long categoryId, String name, String description) {
    //     Category category = new Category(categoryId, name, description);
    //     categoryRepository.save(categoryId, category);
    // }

    // public void deleteCategory(Long categoryId) {
    //     categoryRepository.delete(categoryId);
    // }

    // public Optional<Category> findOneCategory(Long categoryId) {
    //     try {
    //         Category category = categoryRepository.findById(categoryId);
    //         return Optional.of(category);
    //     } catch (CategoryNotFoundException e) {
    //         return Optional.empty();
    //     }
    // }
}
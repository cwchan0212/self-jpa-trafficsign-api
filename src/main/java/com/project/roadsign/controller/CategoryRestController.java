package com.project.roadsign.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.roadsign.entity.Category;
import com.project.roadsign.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController

@RequestMapping("/api/category")
public class CategoryRestController {

    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<Category>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<Category>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> saveCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        Category updateCategory = new Category(categoryId, category.getName(), category.getDescription(), category.getRoadsigns());
        return new ResponseEntity<Category>(categoryService.saveCategory(updateCategory), HttpStatus.CREATED);
    }
    
    @DeleteMapping(value="/{categoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

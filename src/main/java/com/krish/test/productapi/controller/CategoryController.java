package com.krish.test.productapi.controller;

import com.krish.test.productapi.entity.Category;
import com.krish.test.productapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/v1/categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/v1/categories/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable long categoryId) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        if(category.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(category);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(category);
        }
    }
    @PatchMapping("/v1/category/{categoryId}/item/{itemId}")
    public ResponseEntity<Optional<Category>> addPreExistingItemToCategory(@PathVariable long categoryId, @PathVariable long itemId) {
        try{
            Optional<Category> response = categoryService.addPreExistingItemToCategory(categoryId, itemId);
            return ResponseEntity.ok(response);
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

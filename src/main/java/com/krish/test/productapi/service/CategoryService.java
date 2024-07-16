package com.krish.test.productapi.service;

import com.krish.test.productapi.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryById(long categoryId);
    Optional<Category> addPreExistingItemToCategory(long categoryId, long itemId) throws Exception;

}

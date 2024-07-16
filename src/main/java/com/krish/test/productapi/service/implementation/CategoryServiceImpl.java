package com.krish.test.productapi.service.implementation;

import com.krish.test.productapi.entity.Category;
import com.krish.test.productapi.repository.CategoryRepository;
import com.krish.test.productapi.service.CategoryService;
import com.krish.test.productapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(final long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> addPreExistingItemToCategory(final long categoryId, final long itemId) throws Exception {

    }

}

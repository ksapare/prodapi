package com.krish.test.productapi.repository;

import com.krish.test.productapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String name);

    Category findByCategoryIdAndItemId(long categoryId, long itemId);
}

package com.krish.test.productapi.service.implementation;

import com.krish.test.productapi.entity.Item;
import com.krish.test.productapi.repository.CategoryRepository;
import com.krish.test.productapi.entity.Category;
import com.krish.test.productapi.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ItemService itemService;
    Category category;
    Item item;
    List<Category> categories = new ArrayList<>();

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .itemId(119363L)
                .itemName("Samsung 1.6-cu ft Over-the-Range Microwave")
                .itemDescription("Attractive stainless steel exterior complements modern kitchen decor")
                .itemPrice(191f)
                .build();

        category = Category.builder()
                .categoryId(1L)
                .categoryName("Appliances")
                .categoryDescription("A device or piece of equipment designed to perform a specific task, typically a domestic one.")
                .itemList(new ArrayList<>())
                .build();
    }

    @Test
    void TestThat_WhenGetAllCategoryIsCalled_Expect_ListOfAllCategoriesWithDetails(){
        //Arrange
        category.getItemList().add(item);
        categories.add(category);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        //Act
        List<Category> response = categoryService.getCategories();
        //Assert
        Assertions.assertEquals(categories, response);
    }

    @Test
    void TestThat_WhenValidCategoryIdIsGivenToFetchCategoryById_Expect_TheCategoryWithDetails(){
        //Arrange
        category.getItemList().add(item);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        //Act
        Optional<Category> response = categoryService.getCategoryById(1);
        //Assert
        assertEquals(Optional.of(category), response);
    }

    @Test
    void TestThat_WhenInvalidCategoryIdIsGivenToFetchCategoryById_Expect_Null(){
        //Arrange
        Mockito.when(categoryRepository.findById(11L)).thenReturn(Optional.empty());
        //Act
        Optional<Category> response = categoryService.getCategoryById(11);
        //Assert
        assertEquals(Optional.empty(), response);
    }

    @Test
    void TestThat_WhenValidCategoryAndItemId_Expect_PreExistingItemGetsAddedToCategory() {

        // Arrange
        Mockito.when(categoryService.getCategoryById(1L))
                .thenReturn(Optional.ofNullable(category));
        Mockito.when(itemService.findItemById(119363L))
                .thenReturn(Optional.ofNullable(item));
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        //Act
        Optional<Category> response = categoryService.addPreExistingItemToCategory(1L, 119363L);

        //Assert
        category.setItemList(List.of(item));
        verify(categoryRepository, times(1)).save(category);
        assertEquals(Optional.of(category), response);
    }

    void Test_WhenCategoryExistsAndItemNotExists_CreateItemA() {

    }

    void Test_whenCategoryDoesntExist_Return() {

    }

    void Test_whenCategoryExistsAndItemNotExist_AddItemToCategory() {

    }
}
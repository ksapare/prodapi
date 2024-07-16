package com.krish.test.productapi.controller;

import com.krish.test.productapi.entity.Category;
import com.krish.test.productapi.entity.Item;
import com.krish.test.productapi.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    Item item;
    Category category;
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
    void TestThat_WhenAPIToRetrieveAllCategoriesIsHit_Expect_ListOfCategories() throws Exception {
        //Arrange
        category.getItemList().add(item);
        categories.add(category);

        Mockito.when(categoryService.getCategories())
                .thenReturn(categories);

        //Act and Assert
        mockMvc.perform((MockMvcRequestBuilders.get("/v1/categories")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemList[0].itemId").value(119363));
    }

    @Test
    void TestThat_WhenAPIToFetchCategoryInfoByIdIsHitWithValidCategoryId_Expect_DetailsOfTheCategory() throws Exception {
        //Arrange
        category.getItemList().add(item);

        Mockito.when(categoryService.getCategoryById(1))
                .thenReturn(Optional.ofNullable(category));

        //Act and Assert
        mockMvc.perform((MockMvcRequestBuilders.get("/v1/categories/1")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemList[0].itemId").value(119363));
    }

    @Test
    void TestThat_WhenAPIToFetchCategoryInfoByIdIsHitWithInvalidCategoryId_Expect_NullToBeReturned() throws Exception {
        //Arrange
        Mockito.when(categoryService.getCategoryById(12))
                .thenReturn(Optional.empty());

        //Act and Assert
        mockMvc.perform((MockMvcRequestBuilders.get("/v1/categories/12")))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }

    @Test
    public void TestThat_WhenAPIisHitWithValidCategoryAndItemId_Expect_ItemGetsAddedToCategory() throws Exception {
        //Arrange
        category.getItemList().add(item);
        Mockito.when(categoryService.addPreExistingItemToCategory(1L, 119363L)).thenReturn(Optional.of(category));
        //Act and Assert
        mockMvc.perform((MockMvcRequestBuilders.patch("/v1/category/1/item/119363")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemList[0].itemId").value(119363));
    }

    @Test
    void TestThat_WhenInvalidCategoryAndValidItemId_Expect_CategoryNotFoundException() throws Exception {
        //Arrange
        Mockito.when(categoryService.addPreExistingItemToCategory(2L, 119363L))
                .thenThrow(new NoSuchElementException("Category not found"));
        //Act and Assert
        mockMvc.perform((MockMvcRequestBuilders.patch("/v1/category/2/item/119363")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(result -> assertEquals("Category not found", result.getResolvedException().getCause().getMessage()));
    }

    @Test
    void TestThat_WhenValidCategoryAndInvalidItemId_Expect_ItemNotFoundException() throws Exception {

        //Arrange
        Mockito.when(categoryService.addPreExistingItemToCategory(2L, 11936L))
                .thenThrow(new NoSuchElementException("Item not found"));
        //Act and Assert
        mockMvc.perform((MockMvcRequestBuilders.patch("/v1/category/2/item/11936")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertEquals("Item not found", result.getResolvedException().getCause().getMessage()));
    }
}
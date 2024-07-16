# Lesson 2 - Test Driven Development (Edge Cases)

We are now going to write tests for edge case scenarios and implement them. 
We've already identified few edge cases in lesson 1.

### Solution

Let's consider the first edge case and guide its implementation with a TDD approach.

>If the category id is invalid, the response should be "Category not found" with status code 404.

Add the below test to CategoryServiceImplTest

```dtd
    @Test
    void TestThat_WhenInvalidCategoryAndValidItemId_Expect_CategoryNotFoundException() {
        // Arrange
        Mockito.when(categoryRepository.findById(2L))
                .thenReturn(Optional.empty());
        //Act
        try{
            categoryService.addPreExistingItemToCategory(2L, 119363L);
        }
        catch (NoSuchElementException exception){
            //Assert
            assertEquals("Category not found", exception.getMessage());
        }
    }
```

Run the test. It will fail since the functionality has not been implemented yet. 

> Expected :Category not found
Actual   :No value present


We will now refactor the code in CategoryServiceImpl to incorporate expectations for the edge case.

```dtd
 @Override
    public Optional<Category> addPreExistingItemToCategory(final long categoryId, final long itemId) {
        // fetch category, fetch item and add item to the category
        Category category = getCategoryById(categoryId)
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        Optional<Item> item = itemService.findItemById(itemId);
        category.getItemList().add(item.orElse(null));
        
        // save to db
        // return saved category entity
        return Optional.of(categoryRepository.save(category));
    }
```
The test will now pass. 


Next, we will write the test case in controller layer (CategoryController).

Add the below test to CategoryController

```dtd
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
```
Upon running the test, it will fail, so we'll then refactor the existing 
controller (CategoryController) code to make it pass.

```dtd
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
```

Run the test, and it will pass.

Next, we will write test for the second edge case and implement it.

>If the item id is invalid, the response should be "Item not found" with status code 404.

Add the below test to CategoryServiceImplTest

 ```dtd
     @Test
        void TestThat_WhenValidCategoryAndInvalidItemId_Expect_ItemNotFoundException() {
        // Arrange
        Mockito.when(categoryRepository.findById(1L))
        .thenReturn(Optional.ofNullable(category));
        Mockito.when(itemService.findItemById(11936L))
        .thenReturn(Optional.empty());
        //Act
        try{
        categoryService.addPreExistingItemToCategory(1L, 11936L);
        }
        catch (NoSuchElementException exception){
        //Assert
        assertEquals("Item not found", exception.getMessage());
        }
        }
```


We will now refactor the code in CategoryServiceImpl to include the edge case expectation.

```dtd
    @Override
    public Optional<Category> addPreExistingItemToCategory(final long categoryId, final long itemId) {
        // fetch category, fetch item and add item to the category
        Category category = getCategoryById(categoryId)
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        Item item = itemService.findItemById(itemId)
                .orElseThrow(()-> new NoSuchElementException("Item not found"));
        category.getItemList().add(item);
        // save to db
        // return saved category entity
        return Optional.of(categoryRepository.save(category));
    }
```
The test will pass. Let's move on to the controller layer.

Add the below test to CategoryController
```dtd
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
```

Since the exception is handled in service layer, no code refactoring is required in controller
and the test will pass. 

Congratulations...!! You have built the functionality!
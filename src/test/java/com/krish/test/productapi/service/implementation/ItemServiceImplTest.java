package com.krish.test.productapi.service.implementation;

import com.krish.test.productapi.entity.Item;
import com.krish.test.productapi.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;
    Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .itemId(119363L)
                .itemName("Samsung 1.6-cu ft Over-the-Range Microwave")
                .itemDescription("Attractive stainless steel exterior complements modern kitchen decor")
                .itemPrice(191f)
                .build();
    }

    @Test
    void TestThat_WhenValidItemIdIsGivenToFetchItemById_Expect_TheItemWithDetails(){
        //Arrange
        Mockito.when(itemRepository.findById(119363L)).thenReturn(Optional.ofNullable(item));
        //Act
        Optional<Item> response = itemService.findItemById(119363L);
        //Assert
        assertEquals(Optional.of(item), response);
    }


}
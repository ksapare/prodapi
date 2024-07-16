package com.krish.test.productapi.service;

import com.krish.test.productapi.entity.Item;

import java.util.Optional;

public interface ItemService {
    Optional<Item> findItemById(long itemId);
}

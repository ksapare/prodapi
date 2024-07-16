package com.krish.test.productapi.service.implementation;

import com.krish.test.productapi.entity.Item;
import com.krish.test.productapi.repository.ItemRepository;
import com.krish.test.productapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Optional<Item> findItemById(long itemId) {
        return itemRepository.findById(itemId);
    }
}

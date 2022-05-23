package com.example.siteinspring.services.impl;

import com.example.siteinspring.entities.Items;
import com.example.siteinspring.repositories.ItemsRepository;
import com.example.siteinspring.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public Items addItem(Items item) {
        return itemsRepository.save(item);
    }

    @Override
    public void deleteItem(Items item) {
        itemsRepository.delete(item);
    }

    @Override
    public Items getItem(Long id) {
        return itemsRepository.getById(id);
    }

    @Override
    public List<Items> getItems() {
        return itemsRepository.findAll();
    }

    @Override
    public Items saveItem(Items item) {
        return itemsRepository.save(item);
    }

    @Override
    public List<Items> getTopItems() {
        return itemsRepository.findItemsByInTopPageIsTrue();
    }
}

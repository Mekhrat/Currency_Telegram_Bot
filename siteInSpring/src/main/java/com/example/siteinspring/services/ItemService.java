package com.example.siteinspring.services;

import com.example.siteinspring.entities.Items;

import java.util.List;

public interface ItemService {
    Items addItem(Items item);
    void deleteItem(Items item);
    Items getItem(Long id);
    List<Items> getItems();
    Items saveItem(Items item);

    List<Items> getTopItems();

}

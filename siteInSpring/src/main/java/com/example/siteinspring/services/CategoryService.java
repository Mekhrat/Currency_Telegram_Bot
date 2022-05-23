package com.example.siteinspring.services;

import com.example.siteinspring.entities.Categories;
import com.example.siteinspring.entities.Items;

import java.util.List;

public interface CategoryService {
    Categories addCategory(Categories category);
    void deleteCategory(Categories category);
    Categories getCategory(Long id);
    List<Categories> getCategories();
    Categories saveCategory(Categories category);
}

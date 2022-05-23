package com.example.firstjavaspring.services;

import com.example.firstjavaspring.entities.Categories;
import com.example.firstjavaspring.entities.Countries;
import com.example.firstjavaspring.entities.ShopItems;

import java.util.ArrayList;
import java.util.List;

public interface ItemService {
     ShopItems addItem(ShopItems item);
     List<ShopItems> getAllItems();
     ShopItems getItem(Long id);
     void deleteItem(ShopItems item);
     ShopItems saveItem(ShopItems item);

     Countries addCountry(Countries country);
     List<Countries> getCountries();
     Countries getCountry(Long id);
     void deleteCountry(Countries country);
     Countries saveCountry(Countries country);

     Categories addCategory(Categories category);
     List<Categories> getCategories();
     Categories getCategory(Long id);
     Categories saveCategory(Categories category);
     void deleteCategory(Categories category);
}

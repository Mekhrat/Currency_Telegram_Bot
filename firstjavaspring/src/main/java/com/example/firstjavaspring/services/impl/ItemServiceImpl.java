package com.example.firstjavaspring.services.impl;

import com.example.firstjavaspring.entities.Categories;
import com.example.firstjavaspring.entities.Countries;
import com.example.firstjavaspring.entities.ShopItems;
import com.example.firstjavaspring.repositories.CategoriesRepository;
import com.example.firstjavaspring.repositories.CountriesRepository;
import com.example.firstjavaspring.repositories.ShopItemRepository;
import com.example.firstjavaspring.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemRepository shopitemRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public ShopItems addItem(ShopItems item) {
        return shopitemRepository.save(item);
    }

    @Override
    public List<ShopItems> getAllItems() {
        return shopitemRepository.findAllByAmountGreaterThan(-1);
    }

    @Override
    public ShopItems getItem(Long id) {
        return shopitemRepository.getById(id);
    }

    @Override
    public void deleteItem(ShopItems item) {
        shopitemRepository.delete(item);
    }

    @Override
    public ShopItems saveItem(ShopItems item) {
        return shopitemRepository.save(item);
    }


    @Override
    public Countries addCountry(Countries country) {
        return countriesRepository.save(country);
    }

    @Override
    public List<Countries> getCountries() {
        return countriesRepository.findAll();
    }

    @Override
    public Countries getCountry(Long id) {
        return countriesRepository.getById(id);
    }

    @Override
    public void deleteCountry(Countries country) {
        countriesRepository.delete(country);
    }

    @Override
    public Countries saveCountry(Countries country) {
        return countriesRepository.save(country);
    }

    @Override
    public Categories addCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return categoriesRepository.getById(id);
    }

    @Override
    public Categories saveCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    @Override
    public void deleteCategory(Categories category) {
        categoriesRepository.delete(category);
    }
}

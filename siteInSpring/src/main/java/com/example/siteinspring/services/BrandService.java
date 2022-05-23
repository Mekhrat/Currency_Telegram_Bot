package com.example.siteinspring.services;

import com.example.siteinspring.entities.Brands;

import java.util.List;

public interface BrandService {
    Brands addBrand(Brands brand);
    void deleteBrand(Brands brans);
    Brands getBrand(Long id);
    List<Brands> getBrands();
    Brands saveBrand(Brands brand);
}

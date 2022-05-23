package com.example.siteinspring.services.impl;

import com.example.siteinspring.entities.Brands;
import com.example.siteinspring.repositories.BrandsRepository;
import com.example.siteinspring.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandsRepository brandsRepository;

    @Override
    public Brands addBrand(Brands brand) {
        return brandsRepository.save(brand);
    }

    @Override
    public void deleteBrand(Brands brans) {
        brandsRepository.delete(brans);
    }

    @Override
    public Brands getBrand(Long id) {
        return brandsRepository.getById(id);
    }

    @Override
    public List<Brands> getBrands() {
        return brandsRepository.findAll();
    }

    @Override
    public Brands saveBrand(Brands brand) {
        return brandsRepository.save(brand);
    }
}

package com.example.siteinspring.repositories;

import com.example.siteinspring.entities.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BrandsRepository extends JpaRepository<Brands, Long> {
}

package com.example.firstjavaspring.repositories;

import com.example.firstjavaspring.entities.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CountriesRepository extends JpaRepository<Countries,Long> {
}

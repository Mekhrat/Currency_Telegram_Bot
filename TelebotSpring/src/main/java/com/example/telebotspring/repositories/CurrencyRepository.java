package com.example.telebotspring.repositories;

import com.example.telebotspring.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    List<Currency> findCurrenciesByName(String name);
    void deleteAllByName(String name);
    List<Currency> findTop11ByNameOrderByIdDesc(String name);


}

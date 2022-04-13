package com.example.telebotspring.services.impl;

import com.example.telebotspring.entities.Currency;
import com.example.telebotspring.repositories.CurrencyRepository;
import com.example.telebotspring.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public List<Currency> getCurrencyByName(String name) {
        return currencyRepository.findCurrenciesByName(name);
    }

    @Override
    public Currency addCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public void deleteCurrencyByName(String name) {
        currencyRepository.deleteAllByName(name);
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public List<Currency> addCurrencies(List<Currency> currencies) {
        return currencyRepository.saveAll(currencies);
    }

    @Override
    public List<Currency> getTop10(String name) {
        return currencyRepository.findTop11ByNameOrderByIdDesc(name);
    }
}

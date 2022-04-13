package com.example.telebotspring.services;

import com.example.telebotspring.entities.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrencyByName(String name);
    Currency addCurrency(Currency currency);
    void deleteCurrencyByName(String name);
    List<Currency> getCurrencies();
    List<Currency> addCurrencies(List<Currency> currencies);
    List<Currency> getTop10(String name);
}

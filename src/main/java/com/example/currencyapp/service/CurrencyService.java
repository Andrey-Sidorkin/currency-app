package com.example.currencyapp.service;

import com.example.currencyapp.model.Currency;
import java.util.List;

public interface CurrencyService {
    List<Currency> getAvailableCodes();

    Currency findByCode(String code);

    void saveAll(List<Currency> currencies);
}

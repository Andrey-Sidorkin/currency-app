package com.example.currencyapp.service;

import com.example.currencyapp.model.Currency;
import com.example.currencyapp.model.Rate;
import java.time.LocalDate;
import java.util.List;

public interface RateService {
    boolean isExist(LocalDate date, Currency currency);

    Rate save(Rate rate);

    void saveAll(List<Rate> rates);
}

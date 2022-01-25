package com.example.currencyapp.service;

import com.example.currencyapp.dao.CurrencyDao;
import com.example.currencyapp.exception.DataProcessingException;
import com.example.currencyapp.model.Currency;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyDao currencyDao;

    @Override
    public List<Currency> getAvailableCodes() {
        return currencyDao.findAll();
    }

    @Override
    public Currency findByCode(String code) {
        return currencyDao.findByCode(code)
                .orElseThrow(() -> new DataProcessingException(
                        "Can't find currency by code " + code));
    }

    @Override
    public void saveAll(List<Currency> currencies) {
        currencyDao.saveAll(currencies);
    }
}

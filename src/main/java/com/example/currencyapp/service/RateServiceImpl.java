package com.example.currencyapp.service;

import com.example.currencyapp.dao.RateDao;
import com.example.currencyapp.model.Currency;
import com.example.currencyapp.model.Rate;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateDao rateDao;

    @Override
    public boolean isExist(LocalDate date, Currency currency) {
        return rateDao.findRateByDateAndCurrency(date, currency).isPresent();
    }

    @Override
    public Rate save(Rate rate) {
        return rateDao.save(rate);
    }

    @Override
    public void saveAll(List<Rate> rates) {
        rateDao.saveAll(rates);
    }
}

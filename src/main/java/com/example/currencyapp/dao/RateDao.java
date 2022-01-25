package com.example.currencyapp.dao;

import com.example.currencyapp.model.Currency;
import com.example.currencyapp.model.Rate;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateDao extends JpaRepository<Rate, Long> {
    Optional<Rate> findRateByDateAndCurrency(LocalDate date, Currency currency);
}

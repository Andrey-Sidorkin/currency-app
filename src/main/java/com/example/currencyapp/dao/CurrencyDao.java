package com.example.currencyapp.dao;

import com.example.currencyapp.model.Currency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyDao extends JpaRepository<Currency, String> {
    Optional<Currency> findByCode(String code);
}

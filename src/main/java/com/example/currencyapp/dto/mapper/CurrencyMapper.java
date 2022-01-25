package com.example.currencyapp.dto.mapper;

import com.example.currencyapp.dto.CurrencyResponseDto;
import com.example.currencyapp.model.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper implements Mapper<Currency, CurrencyResponseDto> {
    @Override
    public Currency map(CurrencyResponseDto dto) {
        Currency currency = new Currency();
        currency.setCode(dto.getCode());
        currency.setName(dto.getName());
        return currency;
    }
}

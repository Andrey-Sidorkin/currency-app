package com.example.currencyapp.dto.mapper;

import com.example.currencyapp.dto.RateResponseDto;
import com.example.currencyapp.model.Currency;
import com.example.currencyapp.model.Rate;
import com.example.currencyapp.service.CurrencyService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RateMapper implements Mapper<Rate, RateResponseDto> {
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ISO_LOCAL_DATE;
    private final CurrencyService currencyService;

    @Override
    public Rate map(RateResponseDto dto) {
        Rate rate = new Rate();
        rate.setCurrentRate(dto.getCurrentRate());
        Currency currency = currencyService.findByCode(dto.getCurrencyCode());
        rate.setCurrency(currency);
        rate.setDate(LocalDate.parse(dto.getDate(), DATE_PATTERN));
        return rate;
    }
}

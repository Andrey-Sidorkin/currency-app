package com.example.currencyapp.dto.mapper;

import com.example.currencyapp.dto.CurrencyResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CurrencyResponseMapper {
    public CurrencyResponseDto map(String code, String name) {
        CurrencyResponseDto responseDto = new CurrencyResponseDto();
        responseDto.setCode(code);
        responseDto.setName(name);
        return responseDto;
    }
}

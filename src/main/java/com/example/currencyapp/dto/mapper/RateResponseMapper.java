package com.example.currencyapp.dto.mapper;

import com.example.currencyapp.dto.RateApiDto;
import com.example.currencyapp.dto.RateResponseDto;
import org.springframework.stereotype.Component;

@Component
public class RateResponseMapper {
    public RateResponseDto map(RateApiDto apiDto, String code) {
        RateResponseDto responseDto = new RateResponseDto();
        responseDto.setCurrentRate(apiDto.getRates().get(code));
        responseDto.setCurrencyCode(code);
        responseDto.setDate(apiDto.getDate());
        return responseDto;
    }
}

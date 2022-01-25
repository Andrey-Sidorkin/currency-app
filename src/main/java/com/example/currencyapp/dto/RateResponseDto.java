package com.example.currencyapp.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RateResponseDto {
    private String currencyCode;
    private String date;
    private double currentRate;
}

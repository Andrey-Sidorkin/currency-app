package com.example.currencyapp.dto;

import java.util.Map;
import lombok.Data;

@Data
public class RateApiDto {
    private boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;
}

package com.example.currencyapp.dto;

import java.util.Map;
import lombok.Data;

@Data
public class CurrencyApiDto {
    private boolean success;
    private Map<String, String> symbols;
}

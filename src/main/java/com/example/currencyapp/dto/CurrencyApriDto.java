package com.example.currencyapp.dto;

import java.util.Map;
import lombok.Data;

@Data
public class CurrencyApriDto {
    private boolean success;
    private Map<String, String> symbols;
}

package com.example.currencyapp.controller;

import com.example.currencyapp.dto.CurrencyApriDto;
import com.example.currencyapp.dto.CurrencyResponseDto;
import com.example.currencyapp.dto.mapper.CurrencyResponseMapper;
import com.example.currencyapp.dto.mapper.Mapper;
import com.example.currencyapp.model.Currency;
import com.example.currencyapp.service.CurrencyService;
import com.example.currencyapp.service.client.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final String apiKey;
    private final CurrencyService currencyService;
    private final HttpClient client;
    private final Mapper<Currency, CurrencyResponseDto> mapper;
    private final CurrencyResponseMapper responseMapper;

    public CurrencyController(@Value("${access.key}") String apiKey,
                              CurrencyService currencyService, HttpClient client,
                              Mapper<Currency, CurrencyResponseDto> mapper,
                              CurrencyResponseMapper responseMapper) {
        this.apiKey = apiKey;
        this.currencyService = currencyService;
        this.client = client;
        this.mapper = mapper;
        this.responseMapper = responseMapper;
    }

    @PostConstruct
    @GetMapping
    public List<CurrencyResponseDto> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAvailableCodes();
        List<CurrencyResponseDto> dtos = new ArrayList<>();
        if (currencies.isEmpty()) {
            String url = String.format("http://api.exchangeratesapi.io/v1/symbols?access_key=%s",
                    apiKey);
            CurrencyApriDto apiDto = client.getData(CurrencyApriDto.class, url);
            for (Map.Entry<String, String> entry : apiDto.getSymbols().entrySet()) {
                dtos.add(responseMapper.map(entry.getKey(), entry.getValue()));
            }
            currencies = dtos.stream()
                    .map(mapper::map)
                    .collect(Collectors.toList());
            currencyService.saveAll(currencies);
        } else {
            dtos = currencies.stream()
                    .map(c -> responseMapper.map(c.getCode(), c.getName()))
                    .collect(Collectors.toList());
        }
        return dtos;
    }
}

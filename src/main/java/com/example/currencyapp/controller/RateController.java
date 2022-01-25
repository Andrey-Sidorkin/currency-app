package com.example.currencyapp.controller;

import com.example.currencyapp.dto.RateApiDto;
import com.example.currencyapp.dto.RateResponseDto;
import com.example.currencyapp.dto.mapper.RateMapper;
import com.example.currencyapp.dto.mapper.RateResponseMapper;
import com.example.currencyapp.exception.DataProcessingException;
import com.example.currencyapp.model.Rate;
import com.example.currencyapp.service.CurrencyService;
import com.example.currencyapp.service.RateService;
import com.example.currencyapp.service.client.HttpClient;
import com.example.currencyapp.util.CodeValidator;
import com.example.currencyapp.util.DateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rates")
public class RateController {
    private static final String DATE_PATTERN = DateUtil.DATE_PATTERN;
    private final String apiKey;
    private final HttpClient client;
    private final RateService rateService;
    private final CurrencyService currencyService;
    private final RateMapper rateMapper;
    private final RateResponseMapper responseMapper;
    private final CodeValidator validator;

    public RateController(@Value("${access.key}") String apiKey,
                          HttpClient client,
                          RateService rateService,
                          CurrencyService currencyService,
                          RateMapper rateMapper,
                          RateResponseMapper responseMapper,
                          CodeValidator validator) {
        this.apiKey = apiKey;
        this.client = client;
        this.rateService = rateService;
        this.currencyService = currencyService;
        this.rateMapper = rateMapper;
        this.responseMapper = responseMapper;
        this.validator = validator;
    }

    @GetMapping("/{code}")
    public RateResponseDto getRate(@PathVariable("code") String code) {
        if (!validator.test(code)) {
            throw new DataProcessingException("Currency code is not valid");
        }
        String url = String.format("http://api.exchangeratesapi.io/v1/latest"
                + "?access_key=%s&symbols=%s&format=1", apiKey, code);
        RateApiDto apiDto = client.getData(RateApiDto.class, url);
        RateResponseDto responseDto = responseMapper.map(apiDto, code);
        if (!rateService.isExist(LocalDate.now(), currencyService.findByCode(code))) {
            rateService.save(rateMapper.map(responseDto));
        }
        return responseDto;
    }

    @GetMapping("/{code}/history")
    public List<RateResponseDto> getHistory(@PathVariable String code,
                                            @RequestParam(name = "date_from")
                                            @DateTimeFormat(
                                                    pattern = DATE_PATTERN) LocalDate dateFrom,
                                            @RequestParam(name = "date_to")
                                            @DateTimeFormat(
                                                    pattern = DATE_PATTERN) LocalDate dateTo) {
        if (!validator.test(code) || dateFrom.isAfter(dateTo)) {
            throw new DataProcessingException("Invalid parameters");
        }
        List<RateResponseDto> responseDtos = new ArrayList<>();
        while (!dateFrom.isAfter(dateTo)) {
            String url = String.format("http://api.exchangeratesapi.io/v1/%s"
                    + "?access_key=%s&symbols=%s,format=1", dateFrom, apiKey, code);
            RateApiDto apiDto = client.getData(RateApiDto.class, url);
            responseDtos.add(responseMapper.map(apiDto, code));
            dateFrom = dateFrom.plusDays(1);
        }
        List<Rate> rates = responseDtos.stream()
                .map(rateMapper::map)
                .filter(r -> !rateService.isExist(r.getDate(), r.getCurrency()))
                .collect(Collectors.toList());
        rateService.saveAll(rates);
        return responseDtos;
    }
}

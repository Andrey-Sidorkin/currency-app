package com.example.currencyapp.service;

import com.example.currencyapp.dao.CurrencyDao;
import com.example.currencyapp.exception.DataProcessingException;
import com.example.currencyapp.model.Currency;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    @InjectMocks
    private CurrencyServiceImpl currencyService;
    private Currency usd;
    private Currency uah;

    @Mock
    private CurrencyDao currencyDao;

    @BeforeEach
    void setUp() {
        usd = new Currency("USD", "United Stated Dollar");
        uah = new Currency("UAH", "Ukrainian Hryvnia");
    }

    @Test
    void findByCode_oK() {
        Mockito.when(currencyDao.findByCode("UAH")).thenReturn(Optional.of(uah));
        currencyService.saveAll(List.of(usd, uah));
        Assertions.assertEquals(uah, currencyService.findByCode("UAH"));
    }

    @Test
    void findByCode_wrongCode_notOk() {
        Mockito.when(currencyDao.findByCode("XXX")).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(DataProcessingException.class,
                () -> currencyService.findByCode("XXX"));
        Assertions.assertEquals("Can't find currency by code XXX", exception.getMessage());
    }
}

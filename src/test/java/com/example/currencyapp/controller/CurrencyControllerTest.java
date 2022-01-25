package com.example.currencyapp.controller;

import com.example.currencyapp.model.Currency;
import com.example.currencyapp.service.CurrencyService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {
    @MockBean
    private CurrencyService currencyService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getAllCurrencies_ok() {
        List<Currency> currencies = List.of(new Currency("USD", "United Stated Dollar"),
                new Currency("UAH", "Ukrainian Hryvnia"),
                new Currency("RUR", "Russian Rouble"));
        Mockito.when(currencyService.getAvailableCodes()).thenReturn(currencies);
        RestAssuredMockMvc.when()
                .get("/currencies")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].code", Matchers.equalTo("USD"))
                .body("[0].name", Matchers.equalTo("United Stated Dollar"))
                .body("[1].code", Matchers.equalTo("UAH"))
                .body("[1].name", Matchers.equalTo("Ukrainian Hryvnia"))
                .body("[2].code", Matchers.equalTo("RUR"))
                .body("[2].name", Matchers.equalTo("Russian Rouble"));
    }
}

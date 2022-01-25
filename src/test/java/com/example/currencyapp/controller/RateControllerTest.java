package com.example.currencyapp.controller;

import com.example.currencyapp.service.CurrencyService;
import com.example.currencyapp.service.RateService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
import java.util.Collections;
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
class RateControllerTest {
    @MockBean
    private CurrencyController currencyController;
    @MockBean
    private CurrencyService currencyService;
    @MockBean
    private RateService rateService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getRate_ok() {
        Mockito.when(currencyController.getAllCurrencies()).thenReturn(Collections.emptyList());
        Mockito.when(rateService.isExist(LocalDate.now(),
                currencyService.findByCode("USD"))).thenReturn(true);
        RestAssuredMockMvc.given()
                .queryParam("code", "USD")
                .when()
                .get("/rates/USD")
                .then()
                .statusCode(200)
                .body("currencyCode", Matchers.equalTo("USD"))
                .body("date", Matchers.equalTo(LocalDate.now().toString()))
                .body("currentRate", Matchers.instanceOf(float.class));
    }

    @Test
    void getHistory_ok() {
        Mockito.when(currencyController.getAllCurrencies()).thenReturn(Collections.emptyList());
        Mockito.when(rateService.isExist(LocalDate.of(2022,01, 15),
                currencyService.findByCode("USD"))).thenReturn(true);
        Mockito.when(rateService.isExist(LocalDate.of(2022,01, 16),
                currencyService.findByCode("USD"))).thenReturn(true);
        Mockito.when(rateService.isExist(LocalDate.of(2022,01, 17),
                currencyService.findByCode("USD"))).thenReturn(true);
        RestAssuredMockMvc.given()
                .queryParam("code", "USD")
                .queryParam("date_from" , "2022-01-15")
                .queryParam("date_to", "2022-01-17")
                .when()
                .get("/rates/USD/history")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].currencyCode", Matchers.equalTo("USD"))
                .body("[0].date", Matchers.equalTo("2022-01-15"))
                .body("[0].currentRate", Matchers.equalTo(1.141611F))
                .body("[1].currencyCode", Matchers.equalTo("USD"))
                .body("[1].date", Matchers.equalTo("2022-01-16"))
                .body("[1].currentRate", Matchers.equalTo(1.141018F))
                .body("[2].currencyCode", Matchers.equalTo("USD"))
                .body("[2].date", Matchers.equalTo("2022-01-17"))
                .body("[2].currentRate", Matchers.equalTo(1.141025F));
    }
}

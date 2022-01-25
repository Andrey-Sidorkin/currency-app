package com.example.currencyapp.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CodeValidatorTest {
    @InjectMocks
    private CodeValidator validator;

    @Test
    void test_validPattern_ok() {
        Assertions.assertTrue(validator.test("RRR"));
    }

    @Test
    void test_invalidPattern_notOk() {
        Assertions.assertFalse(validator.test("RFTI"));
    }
}

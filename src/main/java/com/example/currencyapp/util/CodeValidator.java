package com.example.currencyapp.util;

import java.util.function.Predicate;
import org.springframework.stereotype.Component;

@Component
public class CodeValidator implements Predicate<String> {
    @Override
    public boolean test(String code) {
        return code.matches("[A-Z]{3}");
    }
}

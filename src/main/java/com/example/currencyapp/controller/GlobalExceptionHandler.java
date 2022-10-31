package com.example.currencyapp.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<?> handleExceptions(Exception e) {
        return new ResponseEntity<>(
                Map.of("success", false, "message", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

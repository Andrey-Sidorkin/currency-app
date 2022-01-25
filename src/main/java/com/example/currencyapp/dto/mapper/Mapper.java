package com.example.currencyapp.dto.mapper;

public interface Mapper<T, D> {
    T map(D dto);
}

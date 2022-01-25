package com.example.currencyapp.model;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @Column(name = "current_rate")
    private double currentRate;
    @ManyToOne
    private Currency currency;

    public Rate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(double currentRate) {
        this.currentRate = currentRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rate rate = (Rate) o;
        return Double.compare(rate.currentRate, currentRate) == 0
                && Objects.equals(id, rate.id) && Objects.equals(date, rate.date)
                && Objects.equals(currency, rate.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, currentRate, currency);
    }

    @Override
    public String toString() {
        return "Rate{"
                + "id=" + id
                + ", date=" + date
                + ", currentRate=" + currentRate
                + ", currency=" + currency
                + '}';
    }
}

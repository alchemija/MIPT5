package com.example.asyncprocessing;

public class ExchangeRate {

    private String currencyCode;
    private String rate;

    public ExchangeRate(String currencyCode, String rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return currencyCode + " - " + rate;
    }
}

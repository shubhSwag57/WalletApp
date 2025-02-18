package com.example.walletApplication.DTO;

import com.example.walletApplication.enums.Currency;

public class TransactionRequest {
    private double amount;
    private Currency currency;

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}

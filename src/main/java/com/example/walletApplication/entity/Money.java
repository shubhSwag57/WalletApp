package com.example.walletApplication.entity;

import com.example.walletApplication.enums.Currency;

public class Money {
    double amount;
    Currency currency;

    public Money(double amount,Currency currency){
        this.amount = amount;
        this.currency = currency;
    }
}

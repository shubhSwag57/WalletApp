package com.example.walletApplication.Exceptions;

public class InsufficientBalanceExpections extends RuntimeException {
    public InsufficientBalanceExpections(String message) {
        super(message);
    }
}

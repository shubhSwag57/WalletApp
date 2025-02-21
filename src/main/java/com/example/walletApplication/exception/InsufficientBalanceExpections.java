package com.example.walletApplication.exception;

public class InsufficientBalanceExpections extends RuntimeException {
    public InsufficientBalanceExpections(String message) {
        super(message);
    }
}

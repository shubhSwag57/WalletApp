package com.example.walletApplication.exception;

public class TransactionIdNotFoundException extends RuntimeException {
    public TransactionIdNotFoundException(String message) {
        super(message);
    }
}

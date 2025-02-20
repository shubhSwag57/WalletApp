package com.example.walletApplication.Exceptions;

public class TransactionIdNotFoundException extends RuntimeException {
    public TransactionIdNotFoundException(String message) {
        super(message);
    }
}

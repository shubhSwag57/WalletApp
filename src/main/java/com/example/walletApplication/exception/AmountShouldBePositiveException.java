package com.example.walletApplication.exception;

public class AmountShouldBePositiveException extends RuntimeException {
    public AmountShouldBePositiveException(String message) {
        super(message);
    }
}

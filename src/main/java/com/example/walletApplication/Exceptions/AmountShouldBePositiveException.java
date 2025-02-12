package com.example.walletApplication.Exceptions;

public class AmountShouldBePositiveException extends RuntimeException {
    public AmountShouldBePositiveException(String message) {
        super(message);
    }
}

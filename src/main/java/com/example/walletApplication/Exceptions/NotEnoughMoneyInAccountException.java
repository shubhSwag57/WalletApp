package com.example.walletApplication.Exceptions;

public class NotEnoughMoneyInAccountException extends RuntimeException {
    public NotEnoughMoneyInAccountException(String message) {
        super(message);
    }
}

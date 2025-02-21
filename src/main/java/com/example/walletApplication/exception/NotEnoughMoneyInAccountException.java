package com.example.walletApplication.exception;

public class NotEnoughMoneyInAccountException extends RuntimeException {
    public NotEnoughMoneyInAccountException(String message) {
        super(message);
    }
}

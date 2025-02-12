package com.example.walletApplication.model;

import com.example.walletApplication.Exceptions.AmountShouldBePositiveException;
import com.example.walletApplication.Exceptions.NotEnoughMoneyInAccountException;

public class Wallet {
    private double balance;
    public Wallet(){
        this.balance = 0.0;
    }
    public synchronized void deposit(double amount) {
        if(amount<=0){
            throw new AmountShouldBePositiveException("Please enter positive amount");

        }
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if(amount<=0){
            throw new AmountShouldBePositiveException("Please enter positive amount");

        }
        if(amount>balance){
            throw  new NotEnoughMoneyInAccountException("Not enough balance");
        }
        balance -= amount;
    }

    public boolean checkBalance(double amount){
        if(amount<=0){
            throw new AmountShouldBePositiveException("Please enter positive amount");

        }
        return this.balance == amount;
    }
}

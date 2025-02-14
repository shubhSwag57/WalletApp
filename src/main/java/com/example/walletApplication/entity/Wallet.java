package com.example.walletApplication.entity;

import com.example.walletApplication.Exceptions.AmountShouldBePositiveException;
import com.example.walletApplication.Exceptions.NotEnoughMoneyInAccountException;
import com.example.walletApplication.enums.Currency;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    private Client client;


    public Wallet(Client client, Currency currency) {
        this.balance = 0;
        this.client = client;
        this.currency = currency;
    }

    public synchronized void deposit(double amount) {
        if(amount<=0){
            throw new AmountShouldBePositiveException("Please enter positive amount");

        }
        this.balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if(amount<=0){
            throw new AmountShouldBePositiveException("Please enter positive amount");

        }
        if(amount>balance){
            throw  new NotEnoughMoneyInAccountException("Not enough balance");
        }
        this.balance -= amount;
    }


    public boolean checkBalance(double balance){
        return this.balance == balance;
    }

    public boolean checkClient(Client client){
        return this.client.equals(client);
    }
}

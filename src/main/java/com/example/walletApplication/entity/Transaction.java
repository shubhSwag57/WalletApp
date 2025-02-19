package com.example.walletApplication.entity;

import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Entity
public class Transaction {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Double amount;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // DEPOSIT, WITHDRAWAL, TRANSFER

    public Transaction(Wallet wallet, Double amount, Currency currency, TransactionType transactionType) {
        this.wallet = wallet;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = LocalDateTime.now();
        this.transactionType = transactionType;
    }

    public Boolean isTransactionTypeTransfer(){
        return transactionType == TransactionType.TRANSFER;
    }

    public Long getTransactionId() {
        return id;
    }
}


package com.example.walletApplication.DTO;

import com.example.walletApplication.entity.Transaction;
import com.example.walletApplication.entity.TransferTransaction;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import lombok.Getter;

import java.time.LocalDateTime;

public class TransactionHistoryRes {
    @Getter
    private Long transactionId;
    @Getter
    private TransactionType transactionType;
    // Getters
    @Getter
    private Double amount;
    @Getter
    private Currency currency;
    @Getter
    private LocalDateTime timestamp;


    public TransactionHistoryRes(Long transactionId, TransactionType transactionType, Double amount, Currency currency, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

}

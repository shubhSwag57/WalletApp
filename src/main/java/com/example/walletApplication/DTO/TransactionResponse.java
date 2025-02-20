package com.example.walletApplication.DTO;

import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
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

    @Getter
    private Long senderId;

    @Getter
    private Long receiverId;


    public TransactionResponse(Long transactionId, TransactionType transactionType, Double amount, Currency currency, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    public TransactionResponse(Long transactionId, TransactionType transactionType, Double amount, Currency currency, LocalDateTime timestamp,Long senderId,Long receiverId) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId  = receiverId;
    }
    public TransactionResponse(){

    }

}

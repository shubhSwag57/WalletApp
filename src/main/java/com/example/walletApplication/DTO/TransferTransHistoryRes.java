package com.example.walletApplication.DTO;

import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import lombok.Getter;

import java.time.LocalDateTime;

public class TransferTransHistoryRes extends TransactionHistoryRes{
    @Getter
    private Long senderId;

    @Getter
    private Long receiverId;

    public TransferTransHistoryRes(Long transactionId, TransactionType transactionType, Double amount, Currency currency, LocalDateTime timestamp, Long senderId, Long receiverId){
        super(transactionId,transactionType,amount,currency,timestamp);
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

}

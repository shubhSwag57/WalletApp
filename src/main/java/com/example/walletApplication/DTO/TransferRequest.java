package com.example.walletApplication.DTO;

import com.example.walletApplication.enums.Currency;
import lombok.Getter;
import lombok.Setter;

public class TransferRequest extends  TransactionRequest{
    @Setter
    @Getter
    private Long receiverId;
    private Double amount;
    private Currency currency;
    // Constructors, getters, setters

    public TransferRequest(Long receiverId, Double amount,Currency currency) {
        super(amount,currency);

        this.receiverId = receiverId;
    }


}

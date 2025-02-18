package com.example.walletApplication.DTO;

import com.example.walletApplication.enums.Currency;

public class TransferRequest {
    private Long receiverId;
    private Double amount;
    private Currency currency;
    // Constructors, getters, setters
    public TransferRequest() {}

    public TransferRequest(Long receiverId, Double amount,Currency currency) {
        this.receiverId = receiverId;
        this.amount = amount;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}

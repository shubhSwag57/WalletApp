package com.example.walletApplication.DTO;

import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor  // Creates a default constructor
@AllArgsConstructor // Creates a constructor with all fields
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter// Only include non-null fields
public class TransactionRequest {
    private double amount;
    private Currency currency;
    private TransactionType type;
    private Long receiverId;

    public TransactionRequest(Double amount, Currency currency,TransactionType type) {
        this.amount = amount;
        this.currency = currency;
        this.type = type;
    }

    public TransactionRequest(Double amount, Currency currency ,TransactionType type,Long receiverId){
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.receiverId = receiverId;
    }

}

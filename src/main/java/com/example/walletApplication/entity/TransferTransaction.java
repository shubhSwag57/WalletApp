package com.example.walletApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.net.ssl.SSLSession;

@RequiredArgsConstructor
@Entity
public class TransferTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false, unique = true)
    @Getter
    private Transaction transaction; // Reference to WalletTransaction

    @ManyToOne
    @Getter
    private Wallet senderWallet; // The wallet that sent the money

    @ManyToOne
    @Getter
    private Wallet receiverWallet; // The wallet that received the money

    public TransferTransaction(Transaction transaction, Wallet senderWallet, Wallet receiverWallet) {
        this.transaction = transaction;
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
    }



    public Long getTransactionID() {
        return transaction.getTransactionId();
    }
}


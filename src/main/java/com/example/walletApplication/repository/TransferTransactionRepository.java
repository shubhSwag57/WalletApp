package com.example.walletApplication.repository;

import com.example.walletApplication.entity.TransferTransaction;
import com.example.walletApplication.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {
    List<TransferTransaction> findAllBySenderWalletOrReceiverWallet(Wallet sender, Wallet receiver);
}


package com.example.walletApplication.repository;

import com.example.walletApplication.DTO.TransactionHistoryRes;
import com.example.walletApplication.entity.Transaction;
import com.example.walletApplication.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
        SELECT new com.example.walletApplication.DTO.TransferTransHistoryRes(
            t.id,
            t.transactionType,
            t.amount,
            t.currency,
            t.timestamp,
            CASE WHEN tt IS NOT NULL THEN tt.senderWallet.client.id ELSE null END,
            CASE WHEN tt IS NOT NULL THEN tt.receiverWallet.client.id ELSE null END
        )
        FROM Transaction t
        LEFT JOIN TransferTransaction tt ON t.id = tt.transaction.id
        WHERE t.wallet = :wallet OR tt.receiverWallet = :wallet
    """)


    List<TransactionHistoryRes> findWalletTransferTransHistory(Wallet wallet);

    @Query("""
        SELECT new com.example.walletApplication.DTO.TransactionHistoryRes(
            t.id,
            t.transactionType,
            t.amount,
            t.currency,
            t.timestamp
        )
        FROM Transaction t
        WHERE t.wallet = :wallet AND t.transactionType IN (com.example.walletApplication.enums.TransactionType.DEPOSIT, com.example.walletApplication.enums.TransactionType.WITHDRAW)
    """)
    List<TransactionHistoryRes> findDepositsAndWithdrawals(Wallet wallet);

    List<Transaction> findByWallet(Wallet wallet);
}


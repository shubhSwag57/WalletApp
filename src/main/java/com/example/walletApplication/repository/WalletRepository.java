package com.example.walletApplication.repository;

import com.example.walletApplication.entity.Client;
import com.example.walletApplication.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByClient(Client client);

}

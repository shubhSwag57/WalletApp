package com.example.walletApplication.services;

import com.example.walletApplication.models.User;

public interface WalletService {
    public String deposit(User user, double amount);
    public String withdraw(User user, double amount);
    public String getBalance(User user);
}

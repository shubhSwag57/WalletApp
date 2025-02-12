package com.example.walletApplication;

import com.example.walletApplication.model.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalletTest {
    @Test
    public void testWalletDepositExpectTrue(){
        Wallet wallet = new Wallet();
        wallet.deposit(100);
        assertTrue(wallet.checkBalance(100));
    }

    @Test
    public void testWalletWithdrawExpectTrue(){
        Wallet wallet = new Wallet();
        wallet.deposit(100);
        wallet.withdraw(10);
        assertTrue(wallet.checkBalance(90));
    }
}

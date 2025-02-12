package com.example.walletApplication.models;

import com.example.walletApplication.Exceptions.AmountShouldBePositiveException;
import com.example.walletApplication.Exceptions.NotEnoughMoneyInAccountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    @Test
    public void testWalletDepositExpectTrue(){
        Wallet wallet = new Wallet();
        wallet.deposit(100);
        assertEquals(100,wallet.getBalance());
    }

    @Test
    public void testWalletWithdrawExpectTrue(){
        Wallet wallet = new Wallet();
        wallet.deposit(100);
        wallet.withdraw(10);
        assertEquals(90,wallet.getBalance());
    }

    @Test
    public void testWithdrawInsufficientAmountExpectNotEnoughMoney(){
        Wallet wallet = new Wallet();
        wallet.deposit(100);
        assertThrows(NotEnoughMoneyInAccountException.class, () -> {
            wallet.withdraw(1000);
        });
    }

    @Test
    public void testDepositNegAmountExpectAmountShouldBePositive(){
        Wallet wallet = new Wallet();
        assertThrows(AmountShouldBePositiveException.class, () -> {
            wallet.deposit(-1000);
        });
    }

    @Test
    public void testWithdrawNegAmountExpectAmountShouldBePositive(){
        Wallet wallet = new Wallet();
        assertThrows(AmountShouldBePositiveException.class, () -> {
            wallet.withdraw(-1000);
        });
    }


}

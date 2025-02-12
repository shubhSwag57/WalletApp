package com.example.walletApplication;

import com.example.walletApplication.Exceptions.AmountShouldBePositiveException;
import com.example.walletApplication.Exceptions.NotEnoughMoneyInAccountException;
import com.example.walletApplication.model.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

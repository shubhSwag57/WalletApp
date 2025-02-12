package com.example.walletApplication.servicesImplementation;

import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletServiceImplTest {
    @Test

    public void testDepositMoney(){
        WalletServiceImpl walletService = new WalletServiceImpl();
        User user = new User("test1","123456");
        String res = walletService.deposit(user,100);
        assertEquals("Deposited: 100.0, Total Balance: 100.0",res);
    }

    @Test
    public void testWithdrawSufficientBalance(){
        WalletServiceImpl walletService = new WalletServiceImpl();
        User user = new User("test1","123456");
        String res = walletService.deposit(user,100);
        res = walletService.withdraw(user,50.0);
        assertEquals("Withdrawn: 50.0, Total Balance: 50.0",res);
    }

    @Test
    public void testWithdrawInSufficientBalance(){
        WalletServiceImpl walletService = new WalletServiceImpl();
        User user = new User("test1","123456");
        String res = walletService.deposit(user,100);
        res = walletService.withdraw(user,50.0);
        assertEquals("Withdrawn: 50.0, Total Balance: 50.0",res);
        res = walletService.withdraw(user,100.0);
        assertEquals(Messages.INSUFFICIENT_BALANCE,res);
    }

    @Test
    public void testGetBalance(){
        WalletServiceImpl walletService = new WalletServiceImpl();
        User user = new User("test1","123456");
        String res = walletService.deposit(user,100.0);
        res = walletService.getBalance(user);
        assertEquals("Total Balance: 100.0",res);


    }

}

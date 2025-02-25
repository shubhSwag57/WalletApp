//package com.example.walletApplication.entity;
//
//import com.example.walletApplication.exception.AmountShouldBePositiveException;
//import com.example.walletApplication.exception.NotEnoughMoneyInAccountException;
//import com.example.walletApplication.enums.Currency;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WalletTest {
//    private Wallet wallet;
//    private Client client;
//
//    @BeforeEach
//    public void setUp() {
//        client = new Client("shubham", "xyz12345");
//        wallet = new Wallet(client, Currency.USD);
//    }
//
//    @Test
//    public void testDepositPositiveAmount() {
//        wallet.deposit(100);
//        assertTrue(wallet.checkBalance(100));
//    }
//
//    @Test
//    public void testDepositNegativeAmountThrowsException() {
//        assertThrows(AmountShouldBePositiveException.class, () -> wallet.deposit(-50));
//    }
//
//    @Test
//    public void testDepositZeroAmountThrowsException() {
//        assertThrows(AmountShouldBePositiveException.class, () -> wallet.deposit(0));
//    }
//
//    @Test
//    public void testWithdrawSufficientBalance() {
//        wallet.deposit(200);
//        wallet.withdraw(100);
//        assertTrue(wallet.checkBalance(100));
//    }
//
//    @Test
//    public void testWithdrawExactBalance() {
//        wallet.deposit(150);
//        wallet.withdraw(150);
//        assertTrue(wallet.checkBalance(0));
//    }
//
//    @Test
//    public void testWithdrawInsufficientBalanceThrowsException() {
//        wallet.deposit(50);
//        assertThrows(NotEnoughMoneyInAccountException.class, () -> wallet.withdraw(100));
//    }
//
//    @Test
//    public void testWithdrawNegativeAmountThrowsException() {
//        assertThrows(AmountShouldBePositiveException.class, () -> wallet.withdraw(-20));
//    }
//
//    @Test
//    public void testWithdrawZeroAmountThrowsException() {
//        assertThrows(AmountShouldBePositiveException.class, () -> wallet.withdraw(0));
//    }
//
//    @Test
//    public void testCheckClientTrue() {
//        assertTrue(wallet.checkClient(client));
//    }
//
//    @Test
//    public void testCheckClientFalse() {
//        Client anotherClient = new Client("john_doe", "password123");
//        assertFalse(wallet.checkClient(anotherClient));
//    }
//
//
//}

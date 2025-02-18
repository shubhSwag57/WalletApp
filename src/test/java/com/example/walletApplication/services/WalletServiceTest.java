package com.example.walletApplication.services;

import com.example.walletApplication.Exceptions.WalletNotFoundException;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletServiceTest {

    private ClientRepository clientRepository;
    private WalletRepository walletRepository;
    private WalletService walletService;
    private Client client;
    private Wallet wallet;
    private String username = "testUser";
    private double amount = 100.0;
    private Currency currency = Currency.INR;
    private  long   userId = 1;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        walletRepository = mock(WalletRepository.class);
        walletService = new WalletService(clientRepository, walletRepository);
        client = new Client(username, "password");
        wallet = new Wallet(client, currency);
    }

    @Test
    void testDepositExpectWalletUpdatedSuccessfully() {
        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
        when(clientRepository.findClientByIdLike(userId)).thenReturn(Optional.of(client));
        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));

        walletService.deposit(userId, amount, currency);

        verify(walletRepository).save(wallet);
        assertTrue(wallet.checkBalance(amount));
    }

    @Test
    void testWithdrawExpectWalletUpdatedSuccessfully() {
        wallet.deposit(200.0);
        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
        when(clientRepository.findClientByIdLike(userId)).thenReturn(Optional.of(client));
        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));

        walletService.withdraw(userId, amount, currency);

        verify(walletRepository).save(wallet);
        assertTrue(wallet.checkBalance(200.0-amount));
    }

    @Test
    void testUnauthorizedAccessToWallet() {
        // Arrange
        Client otherClient = new Client("otherUser", "password");
        Wallet otherWallet = new Wallet(otherClient, currency);
        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
        when(clientRepository.findClientByIdLike(userId)).thenReturn(Optional.of(client));
        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));
        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(2, amount, currency));
    }

    @Test
    void testWalletNotFoundExpectWalletNotFoundException() {
        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
        when(clientRepository.findClientByIdLike(userId)).thenReturn(Optional.of(client));
        when(walletRepository.findByClient(client)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(userId, amount, currency));
    }

    @Test
    void testClientNotFoundExpectWalletNotFoundException() {
        when(clientRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(clientRepository.findClientByIdLike(userId)).thenReturn(Optional.of(client));
        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(userId, amount, currency));
    }


//    @Test
//
//    public void testDepositMoney(){
//        WalletServiceImpl walletService = new WalletServiceImpl();
//        User user = new User("test1","123456");
//        String res = walletService.deposit(user,100);
//        assertEquals("Deposited: 100.0, Total Balance: 100.0",res);
//    }
//
//    @Test
//    public void testWithdrawSufficientBalance(){
//        WalletServiceImpl walletService = new WalletServiceImpl();
//        User user = new User("test1","123456");
//        String res = walletService.deposit(user,100);
//        res = walletService.withdraw(user,50.0);
//        assertEquals("Withdrawn: 50.0, Total Balance: 50.0",res);
//    }

//    @Test
//    public void testWithdrawInSufficientBalance(){
//        WalletServiceImpl walletService = new WalletServiceImpl();
//        User user = new User("test1","123456");
//        String res = walletService.deposit(user,100);
//        res = walletService.withdraw(user,50.0);
//        assertEquals("Withdrawn: 50.0, Total Balance: 50.0",res);
//        res = walletService.withdraw(user,100.0);
//        assertEquals(Messages.INSUFFICIENT_BALANCE,res);
//    }
//
//    @Test
//    public void testGetBalance(){
//        WalletServiceImpl walletService = new WalletServiceImpl();
//        User user = new User("test1","123456");
//        String res = walletService.deposit(user,100.0);
//        res = walletService.getBalance(user);
//        assertEquals("Total Balance: 100.0",res);
//
//
//    }

}

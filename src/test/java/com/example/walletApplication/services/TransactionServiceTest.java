package com.example.walletApplication.services;
//
//import com.example.walletApplication.Exceptions.WalletNotFoundException;
//import com.example.walletApplication.entity.Client;
//import com.example.walletApplication.entity.Wallet;
//import com.example.walletApplication.enums.Currency;
//import com.example.walletApplication.repository.ClientRepository;
//import com.example.walletApplication.repository.TransactionRepository;
//import com.example.walletApplication.repository.TransferTransactionRepository;
//import com.example.walletApplication.repository.WalletRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class WalletServiceTest {
//
//    private ClientRepository clientRepository;
//    private WalletRepository walletRepository;
//    private WalletService walletService;
//    private Client client;
//    private Wallet wallet;
//    private String username = "testUser";
//    private double amount = 100.0;
//    private Currency currency = Currency.INR;
//    private  long   userId = 1;
//    private long senderId = 3;
//    private long receiverId = 4;
//    private Client senderClient;
//    private Client receiverClient;
//    private Wallet senderWallet;
//    private Wallet receiverWallet;
//    private String senderUsername = "senderUser";
//    private String receiverUsername = "receiverUser";
//    private TransactionRepository transactionRepository;
//
//    private TransferTransactionRepository transferTransactionRepository;
//
//
//    @BeforeEach
//    void setUp() {
//        clientRepository = mock(ClientRepository.class);
//        walletRepository = mock(WalletRepository.class);
//        walletService = new WalletService(clientRepository, walletRepository,transactionRepository,transferTransactionRepository);
//        client = new Client(username, "password");
//        wallet = new Wallet(client, currency);
//        senderClient = new Client(senderUsername, "password");
//        receiverClient = new Client(receiverUsername, "password");
//
//        senderWallet = new Wallet(senderClient, currency);
//        receiverWallet = new Wallet(receiverClient, currency);
//    }
//
//    @Test
//    void testDepositExpectWalletUpdatedSuccessfully() {
//        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
//        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
//        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));
//
//        walletService.deposit(userId, amount, currency);
//
//        verify(walletRepository).save(wallet);
//        assertTrue(wallet.checkBalance(amount));
//    }
//
//    @Test
//    void testWithdrawExpectWalletUpdatedSuccessfully() {
//        wallet.deposit(200.0);
//        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
//        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
//        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));
//
//        walletService.withdraw(userId, amount, currency);
//
//        verify(walletRepository).save(wallet);
//        assertTrue(wallet.checkBalance(200.0-amount));
//    }
//
//    @Test
//    void testUnauthorizedAccessToWallet() {
//        // Arrange
//        Client otherClient = new Client("otherUser", "password");
//        Wallet otherWallet = new Wallet(otherClient, currency);
//        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
//        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
//        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));
//        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(2, amount, currency));
//    }
//
//    @Test
//    void testWalletNotFoundExpectWalletNotFoundException() {
//        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
//        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
//        when(walletRepository.findByClient(client)).thenReturn(Optional.empty());
//
//        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(userId, amount, currency));
//    }
//
//    @Test
//    void testClientNotFoundExpectWalletNotFoundException() {
//        when(clientRepository.findByUsername(username)).thenReturn(Optional.empty());
//        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
//        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(userId, amount, currency));
//    }
//
//    @Test
//    void testTransferMoneyExpectWalletUpdatedSuccessfully() {
//        // Setup mock behavior
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(clientRepository.findClientById(receiverId)).thenReturn(Optional.of(receiverClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//        when(walletRepository.findByClient(receiverClient)).thenReturn(Optional.of(receiverWallet));
//
//        senderWallet.deposit(200.0);  // Adding balance to sender's wallet
//
//        walletService.transferMoney(senderId, receiverId, amount, currency);
//
//        // Verify that the transfer occurs
//        verify(walletRepository).save(senderWallet);   // Save the updated sender's wallet
//        verify(walletRepository).save(receiverWallet); // Save the updated receiver's wallet
//
//        assertTrue(senderWallet.checkBalance(100.0));   // Sender's wallet balance should decrease by the transfer amount
//        assertTrue(receiverWallet.checkBalance(100.0));  // Receiver's wallet balance should increase by the transfer amount
//    }
//
//
////    @Test
////
////    public void testDepositMoney(){
////        WalletServiceImpl walletService = new WalletServiceImpl();
////        User user = new User("test1","123456");
////        String res = walletService.deposit(user,100);
////        assertEquals("Deposited: 100.0, Total Balance: 100.0",res);
////    }
////
////    @Test
////    public void testWithdrawSufficientBalance(){
////        WalletServiceImpl walletService = new WalletServiceImpl();
////        User user = new User("test1","123456");
////        String res = walletService.deposit(user,100);
////        res = walletService.withdraw(user,50.0);
////        assertEquals("Withdrawn: 50.0, Total Balance: 50.0",res);
////    }
//
////    @Test
////    public void testWithdrawInSufficientBalance(){
////        WalletServiceImpl walletService = new WalletServiceImpl();
////        User user = new User("test1","123456");
////        String res = walletService.deposit(user,100);
////        res = walletService.withdraw(user,50.0);
////        assertEquals("Withdrawn: 50.0, Total Balance: 50.0",res);
////        res = walletService.withdraw(user,100.0);
////        assertEquals(Messages.INSUFFICIENT_BALANCE,res);
////    }
////
////    @Test
////    public void testGetBalance(){
////        WalletServiceImpl walletService = new WalletServiceImpl();
////        User user = new User("test1","123456");
////        String res = walletService.deposit(user,100.0);
////        res = walletService.getBalance(user);
////        assertEquals("Total Balance: 100.0",res);
////
////
////    }
//
//}

import com.example.walletApplication.DTO.TransactionRequest;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import com.example.walletApplication.Exceptions.WalletNotFoundException;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.TransactionRepository;
import com.example.walletApplication.repository.TransferTransactionRepository;
import com.example.walletApplication.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    private ClientRepository clientRepository;
    private WalletRepository walletRepository;
    private TransactionRepository transactionRepository;
    private TransferTransactionRepository transferTransactionRepository;
    private TransactionService transactionService;
    private Client client;
    private Wallet wallet;
    private long userId = 1;
    private Double amount = 100.0;
    private Currency currency = Currency.INR;
    private Long senderId = (long)3;
    private Long receiverId = (long)4;
    private Client senderClient;
    private Client receiverClient;
    private Wallet senderWallet;
    private Wallet receiverWallet;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        walletRepository = mock(WalletRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        transferTransactionRepository = mock(TransferTransactionRepository.class);
        transactionService = new TransactionService(clientRepository, walletRepository, transactionRepository, transferTransactionRepository);
        client = new Client("testUser", "password");
        wallet = new Wallet(client, currency);
        senderClient = new Client("senderUser", "password");
        receiverClient = new Client("receiverUser", "password");
        senderWallet = new Wallet(senderClient, currency);
        receiverWallet = new Wallet(receiverClient, currency);
    }

    @Test
    void testWithdrawExpectWalletUpdatedSuccessfully() {
        wallet.deposit(200.0);
        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
        when(walletRepository.findByClient(client)).thenReturn(Optional.of(wallet));

        transactionService.transaction(userId, new TransactionRequest(amount, currency, TransactionType.WITHDRAW));

        verify(walletRepository).save(wallet);
        assertTrue(wallet.checkBalance(100.0));
    }

    @Test
    void testUnauthorizedAccessToWallet() {
        when(clientRepository.findClientById(userId)).thenReturn(Optional.empty());
        assertThrows(WalletNotFoundException.class, () -> transactionService.transaction(userId, new TransactionRequest(amount, currency, TransactionType.DEPOSIT)));
    }

    @Test
    void testWalletNotFoundExpectWalletNotFoundException() {
        when(clientRepository.findClientById(userId)).thenReturn(Optional.of(client));
        when(walletRepository.findByClient(client)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> transactionService.transaction(userId, new TransactionRequest(amount, currency, TransactionType.DEPOSIT)));
    }

    @Test
    void testTransferMoneyExpectWalletUpdatedSuccessfully() {
        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
        when(clientRepository.findClientById(receiverId)).thenReturn(Optional.of(receiverClient));
        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findByClient(receiverClient)).thenReturn(Optional.of(receiverWallet));

        senderWallet.deposit(200.0);
        TransactionRequest transactionRequest = new TransactionRequest(amount,currency,TransactionType.TRANSFER,receiverId);
        transactionService.transaction(senderId, transactionRequest);

        verify(walletRepository).save(senderWallet);
        verify(walletRepository).save(receiverWallet);

        assertTrue(senderWallet.checkBalance(100.0));
        assertTrue(receiverWallet.checkBalance(100.0));
    }
}

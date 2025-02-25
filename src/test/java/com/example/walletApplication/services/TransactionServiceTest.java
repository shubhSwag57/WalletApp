//package com.example.walletApplication.services;
//
//import com.example.walletApplication.DTO.TransactionRequest;
//import com.example.walletApplication.DTO.TransactionResponse;
//import com.example.walletApplication.exception.TransactionIdNotFoundException;
//import com.example.walletApplication.exception.WalletNotFoundException;
//import com.example.walletApplication.entity.Client;
//import com.example.walletApplication.entity.Wallet;
//import com.example.walletApplication.enums.Currency;
//import com.example.walletApplication.enums.TransactionType;
//import com.example.walletApplication.repository.ClientRepository;
//import com.example.walletApplication.repository.TransactionRepository;
//import com.example.walletApplication.repository.TransferTransactionRepository;
//import com.example.walletApplication.repository.WalletRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.List;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class TransactionServiceTest {
//    private TransactionService transactionService;
//    private WalletRepository walletRepository;
//    private ClientRepository clientRepository;
//    private TransactionRepository transactionRepository;
//    private TransferTransactionRepository transferTransactionRepository;
//
//    private Client senderClient;
//    private Wallet senderWallet;
//    private Client receiverClient;
//    private Wallet receiverWallet;
//
//    private Long senderId = (long)1;
//    private Long receiverId = (long)2;
//    private double amount = 100.0;
//    private Currency currency = Currency.INR;
//
//    @BeforeEach
//    void setUp() {
//        walletRepository = mock(WalletRepository.class);
//        clientRepository = mock(ClientRepository.class);
//        transactionRepository = mock(TransactionRepository.class);
//        transferTransactionRepository = mock(TransferTransactionRepository.class);
//        transactionService = new TransactionService(clientRepository, walletRepository, transactionRepository, transferTransactionRepository);
//
//        senderClient = new Client("Sender", "password");
//        receiverClient = new Client("Receiver", "password");
//        senderWallet = new Wallet(senderClient, currency);
//        receiverWallet = new Wallet(receiverClient, currency);
//
//        senderWallet.deposit(500.0);
//        receiverWallet.deposit(200.0);
//    }
//
//    @Test
//    void testDepositAmount() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.DEPOSIT);
//        transactionService.transaction(senderId, request);
//
//        assertTrue(senderWallet.checkBalance(600.0));
//        verify(walletRepository, times(1)).save(senderWallet);
//    }
//
//    @Test
//    void testWithdrawAmount() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.WITHDRAW);
//        transactionService.transaction(senderId, request);
//
//        assertTrue(senderWallet.checkBalance(400.0));
//        verify(walletRepository, times(1)).save(senderWallet);
//    }
//
//    @Test
//    void testWithdrawExceedingBalanceThrowsException() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        TransactionRequest request = new TransactionRequest(1000.0, currency, TransactionType.WITHDRAW);
//        assertThrows(RuntimeException.class, () -> transactionService.transaction(senderId, request));
//    }
//
//    @Test
//    void testTransferAmountToReceiver() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(clientRepository.findClientById(receiverId)).thenReturn(Optional.of(receiverClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//        when(walletRepository.findByClient(receiverClient)).thenReturn(Optional.of(receiverWallet));
//
//        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.TRANSFER, receiverId);
//        transactionService.transaction(senderId, request);
//
//        assertTrue(senderWallet.checkBalance(400.0));
//        assertTrue(receiverWallet.checkBalance(300.0));
//        verify(walletRepository, times(1)).save(senderWallet);
//        verify(walletRepository, times(1)).save(receiverWallet);
//    }
//
//    @Test
//    void testTransferWhenReceiverWalletNotFoundThrowsException() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        when(clientRepository.findClientById(receiverId)).thenReturn(Optional.of(receiverClient));
//        when(walletRepository.findByClient(receiverClient)).thenReturn(Optional.empty()); // Receiver wallet not found
//
//        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.TRANSFER, receiverId);
//        assertThrows(WalletNotFoundException.class, () -> transactionService.transaction(senderId, request));
//    }
//
//    @Test
//    void testTransferWhenSenderWalletNotFoundThrowsException() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.empty()); // Sender wallet not found
//
//        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.TRANSFER, receiverId);
//        assertThrows(WalletNotFoundException.class, () -> transactionService.transaction(senderId, request));
//    }
//
//    @Test
//    void testWalletTransactionHistory() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        List<TransactionResponse> mockHistory = new ArrayList<>();
//        when(transactionRepository.findDepositsAndWithdrawals(senderWallet)).thenReturn(mockHistory);
//        when(transactionRepository.findWalletTransferTransHistory(senderWallet)).thenReturn(mockHistory);
//
//        List<TransactionResponse> history = transactionService.walletTransactionHistory(senderId);
//
//        assertNotNull(history);
//        assertEquals(0, history.size());
//    }
//
//    @Test
//    void testWalletTransactionHistoryById_TransactionFound() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        TransactionResponse mockTransaction = new TransactionResponse(1L, TransactionType.DEPOSIT, amount, currency, LocalDateTime.now());
//        when(transactionRepository.findTransactionTypeWithdrawAndDepositByIdAndWallet(1L, senderWallet)).thenReturn(mockTransaction);
//
//        TransactionResponse transaction = transactionService.walletTransactionHistoryById(senderId, 1L);
//
//        assertNotNull(transaction);
////        assertEquals(1L, transaction.getId());
//    }
//
//    @Test
//    void testWalletTransactionHistoryById_TransactionNotFound() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
//        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
//
//        when(transactionRepository.findTransactionTypeWithdrawAndDepositByIdAndWallet(1L, senderWallet)).thenReturn(null);
//        when(transactionRepository.findWalletTransferTransHistoryByIdAndWallet(1L, senderWallet)).thenReturn(null);
//
//        assertThrows(TransactionIdNotFoundException.class, () -> transactionService.walletTransactionHistoryById(senderId, 1L));
//    }
//
//    @Test
//    void testUnauthorizedAccessToWallet() {
//        when(clientRepository.findClientById(senderId)).thenReturn(Optional.empty());
//        assertThrows(WalletNotFoundException.class, () -> transactionService.transaction(senderId, new TransactionRequest(amount, currency, TransactionType.DEPOSIT)));
//    }
//}
//
//

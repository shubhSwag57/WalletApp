package com.example.walletApplication.services;

import com.example.walletApplication.DTO.TransactionRequest;
import com.example.walletApplication.DTO.TransactionResponse;
import com.example.walletApplication.exception.InvalidAmountException;
import com.example.walletApplication.exception.TransactionIdNotFoundException;
import com.example.walletApplication.exception.WalletNotFoundException;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.entity.Transaction;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import com.example.walletApplication.grpc.CurrencyConversionService;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.TransactionRepository;
import com.example.walletApplication.repository.TransferTransactionRepository;
import com.example.walletApplication.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    private TransactionService transactionService;
    private WalletRepository walletRepository;
    private ClientRepository clientRepository;
    private TransactionRepository transactionRepository;
    private TransferTransactionRepository transferTransactionRepository;
    private CurrencyConversionService currencyConversionService;

    private Client senderClient;
    private Wallet senderWallet;
    private Client receiverClient;
    private Wallet receiverWallet;

    private final Long senderId = 1L;
    private final Long receiverId = 2L;
    private final double amount = 100.0;
    private final Currency currency = Currency.INR;

    @BeforeEach
    void setUp() {
        walletRepository = mock(WalletRepository.class);
        clientRepository = mock(ClientRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        transferTransactionRepository = mock(TransferTransactionRepository.class);
        currencyConversionService = mock(CurrencyConversionService.class);

        transactionService = new TransactionService(clientRepository, walletRepository, transactionRepository, transferTransactionRepository, currencyConversionService);

        senderClient = new Client("Sender", "password");
        receiverClient = new Client("Receiver", "password");
        senderWallet = new Wallet(senderClient, currency);
        receiverWallet = new Wallet(receiverClient, currency);

        senderWallet.deposit(500.0);
        receiverWallet.deposit(200.0);

        when(currencyConversionService.convertCurrency(anyString(), anyString(), anyDouble())).thenReturn(amount);
    }

    @Test
    void testDepositAmount() {
        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));

        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.DEPOSIT);
        transactionService.transaction(senderId, request);

        assertTrue(senderWallet.checkBalance(600.0));
        verify(walletRepository).save(senderWallet);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testWithdrawAmount() {
        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));

        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.WITHDRAW);
        transactionService.transaction(senderId, request);

        assertTrue(senderWallet.checkBalance(400.0));
        verify(walletRepository).save(senderWallet);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testTransferAmountToReceiver() {
        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
        when(clientRepository.findClientById(receiverId)).thenReturn(Optional.of(receiverClient));
        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findByClient(receiverClient)).thenReturn(Optional.of(receiverWallet));

        TransactionRequest request = new TransactionRequest(amount, currency, TransactionType.TRANSFER, receiverId);
        transactionService.transaction(senderId, request);

        assertTrue(senderWallet.checkBalance(400.0));
        assertTrue(receiverWallet.checkBalance(300.0));
        verify(walletRepository).save(senderWallet);
        verify(walletRepository).save(receiverWallet);
        verify(transactionRepository).save(any(Transaction.class));
        verify(transferTransactionRepository).save(any());
    }

    @Test
    void testWalletTransactionHistoryById_TransactionNotFound() {
        when(clientRepository.findClientById(senderId)).thenReturn(Optional.of(senderClient));
        when(walletRepository.findByClient(senderClient)).thenReturn(Optional.of(senderWallet));

        when(transactionRepository.findTransactionTypeWithdrawAndDepositByIdAndWallet(1L, senderWallet)).thenReturn(null);
        when(transactionRepository.findWalletTransferTransHistoryByIdAndWallet(1L, senderWallet)).thenReturn(null);

        assertThrows(TransactionIdNotFoundException.class, () -> transactionService.walletTransactionHistoryById(senderId, 1L));
    }
}

//package com.example.walletApplication.controller;
//
//import com.example.walletApplication.DTO.TransactionRequest;
//import com.example.walletApplication.DTO.TransferRequest;
//import com.example.walletApplication.enums.Currency;
//import com.example.walletApplication.messages.Messages;
//import com.example.walletApplication.services.ClientService;
//import com.example.walletApplication.services.WalletService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class WalletControllerTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private WalletService walletService;
//
//    @Mock
//    private ClientService clientService;
//
//    @InjectMocks
//    private WalletController walletController;
//
//    private String username = "testUser";
//    private  long userId = 1;
//    private double amount = 100.0;
//    private long senderId = 1;
//    private long receiverId = 2;
//    private Currency currency = Currency.INR;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
//    }
//
//    @Test
//    void testDepositSuccess() throws Exception {
//        doNothing().when(walletService).deposit(userId, amount, currency);
//
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setAmount(amount);
//        transactionRequest.setCurrency(currency);
//
//        mockMvc.perform(post("/clients/{clientId}/wallets/deposit-transactions", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(Messages.DEPOSITED_SUCCESSFULLY));
//    }
//
//    @Test
//    void testDepositFailure() throws Exception {
//        doThrow(new RuntimeException(Messages.DEPOSITED_FAILED)).when(walletService).deposit(userId, amount, currency);
//
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setAmount(amount);
//        transactionRequest.setCurrency(currency);
//
//        mockMvc.perform(post("/clients/{clientId}/wallets/deposit-transactions", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(Messages.DEPOSITED_FAILED));
//    }
//
//    @Test
//    void testWithdrawSuccess() throws Exception {
//        doNothing().when(walletService).withdraw(userId, amount, currency);
//
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setAmount(amount);
//        transactionRequest.setCurrency(currency);
//
//        mockMvc.perform(post("/clients/{clientId}/wallets/withdraw-transactions", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(Messages.WITHDRAW_SUCCESSFULLY));
//    }
//
//    @Test
//    void testWithdrawFailure() throws Exception {
//        doThrow(new RuntimeException(Messages.WITHDRAW_FAILED)).when(walletService).withdraw(userId, amount, currency);
//
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setAmount(amount);
//        transactionRequest.setCurrency(currency);
//
//        mockMvc.perform(post("/clients/{clientId}/wallets/withdraw-transactions", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(Messages.WITHDRAW_FAILED));
//    }
//
//    @Test
//    void testTransferMoneySuccess() throws Exception {
//        // Arrange
//        doNothing().when(walletService).transferMoney(senderId, receiverId, amount, currency);
//
//        TransferRequest transferRequest = new TransferRequest();
//        transferRequest.setReceiverId(receiverId);
//        transferRequest.setAmount(amount);
//        transferRequest.setCurrency(currency);
//
//        // Act & Assert
//        mockMvc.perform(post("/clients/{clientId}/wallets/transfer-money", senderId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transferRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(Messages.TRANSFER_SUCCESSFULLY));
//    }
//}

package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.TransactionRequest;
import com.example.walletApplication.DTO.TransactionResponse;
import com.example.walletApplication.exception.InvalidAmountException;
import com.example.walletApplication.exception.WalletNotFoundException;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.enums.TransactionType;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTests {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final long clientId = 1L;
    private final long transId = 1L;
    private final double amount = 100.0;
    private final Currency currency = Currency.INR;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testGetWalletTransactionHistorySuccessWithEmptyTransactions() throws Exception {
        List<TransactionResponse> history = Collections.emptyList();
        when(transactionService.walletTransactionHistory(clientId)).thenReturn(history);

        mockMvc.perform(get("/clients/{clientId}/wallets/transactions", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetWalletTransactionHistorySuccess() throws Exception {
        List<TransactionResponse> history = List.of(
                new TransactionResponse(1L, TransactionType.DEPOSIT, 200.0, Currency.USD, LocalDateTime.now()),
                new TransactionResponse(2L, TransactionType.WITHDRAW, 50.0, Currency.EUR, LocalDateTime.now())
        );
        when(transactionService.walletTransactionHistory(clientId)).thenReturn(history);

        mockMvc.perform(get("/clients/{clientId}/wallets/transactions", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void testGetWalletTransactionHistoryNotFound() throws Exception {
        when(transactionService.walletTransactionHistory(clientId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/clients/{clientId}/wallets/transactions", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetWalletTransactionHistoryByIdSuccess() throws Exception {

        TransactionResponse history = new TransactionResponse(12L, TransactionType.WITHDRAW, 100.0, Currency.INR, LocalDateTime.now());
        when(transactionService.walletTransactionHistoryById(clientId,transId)).thenReturn(history);

        mockMvc.perform(get("/clients/{clientId}/wallets/transactions/{transId}", clientId, transId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testCreateTransactionSuccess() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);
        transactionRequest.setCurrency(currency);
        transactionRequest.setType(TransactionType.DEPOSIT);

        doNothing().when(transactionService).transaction(clientId, transactionRequest);

        mockMvc.perform(post("/clients/{clientId}/wallets/transactions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(Messages.TRANSACTION_SUCCESSFULLY));
    }

    @Test
    void testCreateTransactionFailure() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);
        transactionRequest.setCurrency(currency);
        transactionRequest.setType(TransactionType.WITHDRAW);

        doThrow(new RuntimeException(Messages.TRANSACTION_FAILED)).when(transactionService).transaction(clientId, transactionRequest);

        mockMvc.perform(post("/clients/{clientId}/wallets/transactions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.TRANSACTION_FAILED));
    }

    @Test
    void testCreateTransactionForUnauthorizedClient() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest(amount, currency, TransactionType.TRANSFER);

        doThrow(new WalletNotFoundException(Messages.WALLET_NOT_FOUND)).when(transactionService).transaction(clientId, transactionRequest);

        mockMvc.perform(post("/clients/{clientId}/wallets/transactions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.WALLET_NOT_FOUND));
    }

    @Test
    void testCreateTransactionInvalidCurrency() throws Exception {
        String invalidRequest = "{ \"amount\": 100.0, \"currency\": \"INVALID\", \"type\": \"DEPOSIT\" }";

        mockMvc.perform(post("/clients/{clientId}/wallets/transactions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTransactionInvalidAmount() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest(-50.0, currency, TransactionType.DEPOSIT);
        doThrow(new InvalidAmountException(Messages.AMOUNT_SHOULD_BE_POSITIVE))
                .when(transactionService).transaction(eq(clientId), any(TransactionRequest.class));

        mockMvc.perform(post("/clients/{clientId}/wallets/transactions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.AMOUNT_SHOULD_BE_POSITIVE));
    }


}

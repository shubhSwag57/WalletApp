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

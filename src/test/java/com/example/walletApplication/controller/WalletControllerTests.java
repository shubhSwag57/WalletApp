package com.example.walletApplication.controller;

import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.ClientService;
import com.example.walletApplication.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTests {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private WalletController walletController;

    private String username = "testUser";
    private double amount = 100.0;
    private Currency currency = Currency.INR;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    void testDepositSuccess() throws Exception {
        doNothing().when(walletService).deposit(username, amount, currency);

        mockMvc.perform(post("/wallet/{username}/deposit", username)
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency.name()))
                .andExpect(status().isOk())
                .andExpect(content().string(Messages.DEPOSITED_SUCCESSFULLY));
    }

    @Test
    void testDepositFailure() throws Exception {
        // Arrange
        doThrow(new RuntimeException(Messages.DEPOSITED_FAILED)).when(walletService).deposit(username, amount, currency);

        // Act and Assert
        mockMvc.perform(post("/wallet/{username}/deposit", username)
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency.name()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.DEPOSITED_FAILED));
    }

    @Test
    void testWithdrawSuccess() throws Exception {
        // Arrange
        doNothing().when(walletService).withdraw(username, amount, currency);

        // Act and Assert
        mockMvc.perform(post("/wallet/{username}/withdraw", username)
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency.name()))
                .andExpect(status().isOk())
                .andExpect(content().string(Messages.WITHDRAW_SUCCESSFULLY));
    }

    @Test
    void testWithdrawFailure() throws Exception {
        doThrow(new RuntimeException(Messages.WITHDRAW_FAILED)).when(walletService).withdraw(username, amount, currency);

        mockMvc.perform(post("/wallet/{username}/withdraw", username)
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency.name()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.WITHDRAW_FAILED));
    }
}

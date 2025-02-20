package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.TransactionRequest;
import com.example.walletApplication.DTO.TransactionResponse;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.TransactionService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@OpenAPIDefinition(info = @Info(title = "Wallet API", version = "1.0", description = "API for Wallet Management"))

@RequestMapping("/clients/{clientId}/wallets/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get wallet transaction history", description = "Fetches the transaction history for a specific client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction history fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid clientId provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaction history not found", content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<List<TransactionResponse>> getWalletHistory(@PathVariable Long clientId) {
        List<TransactionResponse> transactionHistory = transactionService.walletHistory(clientId);
        return ResponseEntity.ok(transactionHistory);
    }

    @Operation(summary = "Get transaction history by ID", description = "Fetches a specific transaction's history for a client.")
    @GetMapping("/{transId}")
    public ResponseEntity<TransactionResponse> getWalletTransactionHistoryById(
            @PathVariable Long clientId, @PathVariable Long transId) {
        TransactionResponse history = transactionService.walletTransactionHistoryById(clientId,transId);
        return ResponseEntity.ok(history);
    }

    @Operation(summary = "Create a new transaction", description = "Records a new transaction for the client.")
    @PostMapping("")
    public ResponseEntity<String> createTransaction(
            @PathVariable long clientId,
            @RequestBody(required = true) TransactionRequest transactionRequest) {
        try {
            transactionService.transaction(clientId, transactionRequest);
            return new ResponseEntity<>(Messages.TRANSACTION_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

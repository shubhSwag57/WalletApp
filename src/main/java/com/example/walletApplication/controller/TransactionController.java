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
@OpenAPIDefinition(info = @Info(title = Messages.API_NAME, version = "1.0", description = Messages.API_NAME))

@RequestMapping("/clients/{clientId}/wallets/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Operation(summary = Messages.GET_WALLET_HISTORY, description = Messages.GET_WALLET_HISTORY_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.TRANSACTION_HISTORY_FETCH_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = Messages.INVALID_CREDENTIALS, content = @Content),
            @ApiResponse(responseCode = "404", description = Messages.TRANSACTION_HISTORY_NOT_FOUND, content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<List<TransactionResponse>> getWalletTransactionHistory(@PathVariable Long clientId) {
        List<TransactionResponse> transactionHistory = transactionService.walletTransactionHistory(clientId);
        return ResponseEntity.ok(transactionHistory);
    }


    @Operation(summary = Messages.GET_WALLET_HISTORY_BY_ID)
    @GetMapping("/{transId}")
    public ResponseEntity<TransactionResponse> getWalletTransactionHistoryById(
            @PathVariable Long clientId, @PathVariable Long transId) {
        TransactionResponse history = transactionService.walletTransactionHistoryById(clientId,transId);
        return ResponseEntity.ok(history);
    }

    @Operation(summary = Messages.CREATE_NEW_TRANSACTION)
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

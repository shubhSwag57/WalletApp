package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.TransactionHistoryRes;
import com.example.walletApplication.services.WalletService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients/{clientId}")
public class TransactionHistoryController {

    private final WalletService walletService;

    public TransactionHistoryController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/transaction-history")
    public ResponseEntity<List<TransactionHistoryRes>> getWalletHistory(@PathVariable Long clientId) {
        List<TransactionHistoryRes> history = walletService.walletHistory(clientId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/transaction-history/{transId}")
    public  ResponseEntity<List<TransactionHistoryRes>> getWalletHistoryById(@PathVariable Long clientId,@PathVariable Long transId) {
        List<TransactionHistoryRes> history = walletService.walletTransactionHistoryById(clientId);
        return ResponseEntity.ok(history);
    }


}

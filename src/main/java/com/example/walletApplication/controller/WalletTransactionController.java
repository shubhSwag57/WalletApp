package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.TransactionRequest;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/{clientId}/wallets")
public class WalletTransactionController {

    private WalletService walletService;

    @Autowired
    public WalletTransactionController(WalletService walletService){
        this.walletService = walletService;
    }
    @PostMapping("/deposit-transactions")
    public ResponseEntity<String> deposit(@PathVariable long clientId, @RequestBody TransactionRequest transactionRequest) {
        try {
            walletService.deposit(clientId, transactionRequest.getAmount(), transactionRequest.getCurrency());
            return new ResponseEntity<>(Messages.DEPOSITED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/withdraw-transactions")
    public ResponseEntity<String> withdraw(@PathVariable long clientId, @RequestBody TransactionRequest transactionRequest) {
        try {
            walletService.withdraw(clientId, transactionRequest.getAmount(), transactionRequest.getCurrency());
            return new ResponseEntity<>(Messages.WITHDRAW_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

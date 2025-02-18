package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.TransactionRequest;
import com.example.walletApplication.DTO.TransferRequest;
import com.example.walletApplication.enums.TransactionType;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/{clientId}/wallets")
public class WalletController {

    private WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService){
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

    @PostMapping("/transactions")
    public ResponseEntity<String> Transaction(
            @PathVariable long clientId,
            @RequestParam TransactionType type, // Using enum as query parameter
            @RequestBody TransactionRequest transactionRequest) {
        try {
            if (type == TransactionType.DEPOSIT) {
                walletService.deposit(clientId, transactionRequest.getAmount(), transactionRequest.getCurrency());
                return new ResponseEntity<>(Messages.DEPOSITED_SUCCESSFULLY, HttpStatus.OK);
            } else if (type == TransactionType.WITHDRAW) {
                walletService.withdraw(clientId, transactionRequest.getAmount(), transactionRequest.getCurrency());
                return new ResponseEntity<>(Messages.WITHDRAW_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid transaction type", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transfer-money")
    public ResponseEntity<String> Transfer(
            @PathVariable long clientId,
            @RequestBody TransferRequest transferRequest){
        try{
            walletService.transferMoney(clientId, transferRequest.getReceiverId(), transferRequest.getAmount(),transferRequest.getCurrency());
            return new ResponseEntity<>(Messages.TRANSFER_SUCCESSFULLY, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}

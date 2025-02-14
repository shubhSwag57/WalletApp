package com.example.walletApplication.controller;

import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.ClientService;
import com.example.walletApplication.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private ClientService clientService;

    private WalletService walletService;


    @PostMapping("/{username}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String username, @RequestParam double amount, @RequestParam Currency currency) {
        try {
            walletService.deposit(username, amount, currency);
            return new ResponseEntity<>(Messages.DEPOSITED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{username}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String username, @RequestParam double amount, @RequestParam Currency currency) {
        try {
            walletService.withdraw(username, amount, currency);
            return new ResponseEntity<>(Messages.WITHDRAW_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

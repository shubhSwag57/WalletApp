package com.example.walletApplication.controller;

import com.example.walletApplication.Exceptions.InsufficientBalanceExpections;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.models.User;
import com.example.walletApplication.services.UserService;
import com.example.walletApplication.services.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final UserService userService;
    private final WalletService walletService;

    public WalletController(UserService userService, WalletService walletService){
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,@RequestParam String password){
        return userService.register(username,password);
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String username,@RequestParam String password, @RequestParam double amount){
        User user = userService.authenticate(username,password);
        if(user == null){
            return Messages.INVALID_CREDENTIALS;
        }

        return walletService.deposit(user,amount);

    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String username,@RequestParam String password, @RequestParam double amount){
        User user = userService.authenticate(username,password);
        if(user == null){
            return Messages.INVALID_CREDENTIALS;
        }
        return walletService.withdraw(user,amount);
    }


    @GetMapping("/balance")
    public String getBalance(@RequestParam String username, @RequestParam String password){
        User user = userService.authenticate(username,password);

        if(user == null){
            return Messages.INVALID_CREDENTIALS;
        }
        return walletService.getBalance(user);
    }
}

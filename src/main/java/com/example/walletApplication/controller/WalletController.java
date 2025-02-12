package com.example.walletApplication.controller;

import com.example.walletApplication.Exceptions.InsufficientBalanceExpections;
import com.example.walletApplication.Exceptions.NotEnoughMoneyInAccountException;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class WalletController {
    private HashMap<String, User> users = new HashMap<>();

    @PostMapping
    public String register(@RequestParam String username,@RequestParam String password){
        if(users.containsKey(username)){
            return Messages.USER_ALREADY_EXISTS;
        }
        users.put (username,new User(username,password));
        return Messages.USER_REGISTERED_SUCCESSFULLY;
    }

    @PostMapping
    public String deposit(@RequestParam String username,@RequestParam String password, @RequestParam double amount){
        User user = users.get(username);
        if(user == null || !user.isValidPassword(password)){
            return Messages.INVALID_CREDENTIALS;
        }
        user.deposit(amount);
        return "Deposited "+ amount;

    }
    @PostMapping
    public String withdraw(@RequestParam String username,@RequestParam String password, @RequestParam double amount){
        User user = users.get(username);
        if(user == null || !user.isValidPassword(password)){
            return Messages.INVALID_CREDENTIALS;
        }
        try{
            user.withdraw(amount);
            return "Withdrawn: "+ amount;
        } catch (Exception e) {
            throw new InsufficientBalanceExpections("Not enough money");
        }
    }

    @GetMapping
    public String getBalance(@RequestParam String username, @RequestParam String password){
        User user = users.get(username);
        if(user == null || !user.isValidPassword(password)){
            return Messages.INVALID_CREDENTIALS;
        }
        return "Balance: "+ user.getBalance();
    }
}

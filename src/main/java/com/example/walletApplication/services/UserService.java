package com.example.walletApplication.services;

import com.example.walletApplication.models.User;

public interface UserService {
    public String register(String username,String password);
    public User authenticate(String username, String password);

}

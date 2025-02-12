package com.example.walletApplication.servicesImplementation;

import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.models.User;
import com.example.walletApplication.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class UserServiceImpl implements UserService {
    private HashMap<String, User> users = new HashMap<>();

    @Override
    public String register(String username,String password){
        if(users.containsKey(username)){
            return Messages.USER_ALREADY_EXISTS;
        }
        users.put (username,new User(username,password));
        return Messages.USER_REGISTERED_SUCCESSFULLY;
    }

    @Override
    public User authenticate(String username,String password){
        User user = users.get(username);
        if(users != null && user.isValidPassword(password)){
            return user;
        }
        return null;
    }

}

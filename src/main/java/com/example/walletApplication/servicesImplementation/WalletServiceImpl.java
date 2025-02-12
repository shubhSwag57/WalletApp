package com.example.walletApplication.servicesImplementation;

import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.models.User;
import com.example.walletApplication.services.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Override
    public String deposit(User user, double amount){
        user.deposit(amount);
        return "Deposited: "+amount +", Total Balance: "+user.getBalance();
    }

    @Override
    public String withdraw(User user, double amount){
        if(user.getBalance()>= amount){
            user.withdraw(amount);
            return "Withdrawn: "+amount+ ", Total Balance: "+ user.getBalance();
        }
        return Messages.INSUFFICIENT_BALANCE;
    }

    @Override
    public String getBalance(User user){
        return "Total Balance: "+user.getBalance();
    }


}

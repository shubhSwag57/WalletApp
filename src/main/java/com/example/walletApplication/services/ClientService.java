package com.example.walletApplication.services;

import com.example.walletApplication.Exceptions.InvalidCredentialsException;
import com.example.walletApplication.Exceptions.UserAlreadyExistsException;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService  {

    private ClientRepository clientRepository;
    private WalletRepository walletRepository;

    public ClientService(ClientRepository clientRepository, WalletRepository walletRepository) {
        this.clientRepository = clientRepository;
        this.walletRepository = walletRepository;
    }


    public void register(Client client){
        List<Client> clients = clientRepository.findAll();
        for(Client c : clients) {
            if(c.equals(client)) {
                throw new UserAlreadyExistsException(Messages.USER_ALREADY_EXISTS);
            }
        }
        clientRepository.save(client);
        Wallet wallet = new Wallet(client, Currency.INR);
        walletRepository.save(wallet);
    }

    public Client login(String username, String password){
        return clientRepository.findByUsername(username)
                .filter(client -> client.isValidPassword(password))
                .orElseThrow(() -> new InvalidCredentialsException(Messages.INVALID_CREDENTIALS));
    }

}

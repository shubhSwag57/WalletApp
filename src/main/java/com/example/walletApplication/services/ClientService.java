package com.example.walletApplication.services;

import com.example.walletApplication.DTO.ClientRequest;
import com.example.walletApplication.exception.InvalidCredentialsException;
import com.example.walletApplication.exception.UserAlreadyExistsException;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.enums.Currency;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService  {


    private ClientRepository clientRepository;
    private WalletRepository walletRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, WalletRepository walletRepository,PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void register(@NotNull ClientRequest clientRequest){
        String encodedPassword = passwordEncoder.encode(clientRequest.getPassword());
        System.out.println(encodedPassword);
        Client client = new Client(clientRequest.getUsername(),encodedPassword);
        List<Client> clients = clientRepository.findAll();

        for(Client c : clients) {
            if(c.isSameUserName(clientRequest.getUsername())) {
                throw new UserAlreadyExistsException(Messages.USER_ALREADY_EXISTS);
            }
        }

        clientRepository.save(client);
        Wallet wallet = new Wallet(client, Currency.INR);
        walletRepository.save(wallet);
    }

    public Client login(@NotNull ClientRequest clientRequest){

        String username = clientRequest.getUsername();
        String password = clientRequest.getPassword();
        return clientRepository.findByUsername(username)
                .filter(client -> client.isValidPassword(password,passwordEncoder))
                .orElseThrow(() -> new InvalidCredentialsException(Messages.INVALID_CREDENTIALS));
    }

}

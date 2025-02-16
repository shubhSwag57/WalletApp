package com.example.walletApplication.controller;

import com.example.walletApplication.entity.Client;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class ClientController {

    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Client client) {
        try {
            clientService.register(client);
            return new ResponseEntity<>(Messages.USER_REGISTERED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestParam String username, @RequestParam String password) {
        try {
            Client client = clientService.login(username, password);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<Client> login(@RequestBody Client client) {
//        try {
//            Client authenticatedClient = clientService.login(client.getUsername(), client.getPassword());
//            return new ResponseEntity<>(authenticatedClient, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
//    }

}

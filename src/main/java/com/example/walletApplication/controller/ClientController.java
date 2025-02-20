package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.ClientRequest;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.ClientService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private  ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @PostMapping("/registerClient")
    public ResponseEntity<String> register(@RequestBody ClientRequest clientRequest) {
        try {
            clientService.register(clientRequest);
            return new ResponseEntity<>(Messages.USER_REGISTERED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginClient")
    public ResponseEntity<String> login(@RequestBody ClientRequest clientRequest) {
        try {
            Client client = clientService.login(clientRequest);
            return new ResponseEntity<>(Messages.USER_LOGGEDIN, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}

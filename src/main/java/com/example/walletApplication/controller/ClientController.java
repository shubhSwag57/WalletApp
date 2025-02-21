package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.ClientRequest;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.services.ClientService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client APIs")
@OpenAPIDefinition(info = @Info(title = Messages.API_NAME, version = "1.0", description = Messages.API_NAME))

public class ClientController {

    private  ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @PostMapping("/registerClient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.USER_REGISTERED_SUCCESSFULLY),
            @ApiResponse(responseCode = "400", description = Messages.BAD_REQUEST)
    })


    public ResponseEntity<String> register(
            @Valid @RequestBody ClientRequest clientRequest)
    {
        try {
            clientService.register(clientRequest);
            return new ResponseEntity<>(Messages.USER_REGISTERED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Messages.USER_REGISTERATION_FAILED, HttpStatus.BAD_REQUEST);
        }

    }
    

    @PostMapping("/loginClient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.USER_LOGGEDIN),
            @ApiResponse(responseCode = "401", description = Messages.INVALID_CREDENTIALS)
    })

    public ResponseEntity<String> login(@RequestBody ClientRequest clientRequest) {
        try {
            Client client = clientService.login(clientRequest);
            return new ResponseEntity<>(Messages.USER_LOGGEDIN, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Messages.USER_LOGIN_FAILED, HttpStatus.UNAUTHORIZED);
        }
    }

}

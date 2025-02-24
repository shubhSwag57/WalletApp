package com.example.walletApplication.controller;

import com.example.walletApplication.DTO.ClientRequest;
import com.example.walletApplication.messages.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.walletApplication.entity.Client;
import com.example.walletApplication.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    public static final String USER_REGISTER_URL = "/clients/registerClient";
    public static final String USER_LOGIN_URL = "/clients/loginClient";
    public static final String REQ_PARAM_USERNAME = "username";
    public static final String REQ_PARAM_PASSWORD = "password";

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientRequest clientRequest;
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        clientRequest = new ClientRequest("testUser", "testPassword");
    }

    @Test
    void testRegisterClientSuccess() throws Exception {
        doNothing().when(clientService).register(any(ClientRequest.class));
        mockMvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(content().string(Messages.USER_REGISTERED_SUCCESSFULLY));
    }


    @Test
    void testRegisterClientFailure() throws Exception {
        doThrow(new RuntimeException(Messages.USER_ALREADY_EXISTS)).when(clientService).register(any(ClientRequest.class));
        mockMvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.USER_ALREADY_EXISTS));
    }



    @Test
    void testLoginFailure() throws Exception {
        ClientRequest newClient = new ClientRequest("testUser","wrongPassword");
        Client client = new Client("testUser","testPassword");
        when(clientService.login(newClient))
                .thenThrow(new RuntimeException(Messages.INVALID_CREDENTIALS));
        mockMvc.perform(post(USER_LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isUnauthorized());
    }




}
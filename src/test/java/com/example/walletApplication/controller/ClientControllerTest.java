package com.example.walletApplication.controller;

import com.example.walletApplication.messages.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.walletApplication.Exceptions.UserAlreadyExistsException;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    public static final String USER_REGISTER_URL = "/user/register";
    public static final String USER_LOGIN_URL = "/user/login";
    public static final String REQ_PARAM_USERNAME = "username";
    public static final String REQ_PARAM_PASSWORD = "password";

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private Client client;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        // Initialize the mock environment for each test
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Sample client object to use in tests
        client = new Client("testUser", "testPassword");
    }

    @Test
    void testRegisterClientSuccess() throws Exception {
        doNothing().when(clientService).register(any(Client.class));
        mockMvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(content().string(Messages.USER_REGISTERED_SUCCESSFULLY));  // Verify response message
    }

    @Test
    void testRegisterClientFailure() throws Exception {
        doThrow(new RuntimeException(Messages.USER_ALREADY_EXISTS)).when(clientService).register(any(Client.class));
        mockMvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.USER_ALREADY_EXISTS));
    }

    @Test
    void testLoginSuccess() throws Exception {
        when(clientService.login("testUser", "testPassword")).thenReturn(client);

        mockMvc.perform(post(USER_LOGIN_URL)
                        .param(REQ_PARAM_USERNAME, "testUser")
                        .param(REQ_PARAM_PASSWORD, "testPassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void testLoginFailure() throws Exception {
        when(clientService.login("testUser", "wrongPassword")).thenThrow(new RuntimeException(Messages.INVALID_CREDENTIALS));

        mockMvc.perform(post(USER_LOGIN_URL)
                        .param(REQ_PARAM_USERNAME, "testUser")
                        .param(REQ_PARAM_PASSWORD, "wrongPassword"))
                .andExpect(status().isUnauthorized());
    }


}
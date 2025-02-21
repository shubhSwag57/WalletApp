package com.example.walletApplication.services;

import com.example.walletApplication.DTO.ClientRequest;
import com.example.walletApplication.exception.InvalidCredentialsException;
import com.example.walletApplication.exception.UserAlreadyExistsException;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private ClientRepository clientRepository;
    private WalletRepository walletRepository;
    private ClientService clientService;
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        walletRepository = mock(WalletRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        clientService = new ClientService(clientRepository, walletRepository,passwordEncoder);
    }


    @Test
    public void testRegisterNewUserExpectRegisteredSuccessfully() {
        String username = "testUser";
        String password = "testPassword";
        ClientRequest clientRequest = new ClientRequest(username, password);

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        clientService.register(clientRequest);

        verify(clientRepository).save(any(Client.class));
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    public void testExistingUserExpectUserAlreadyExists() {
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedTestPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        Client existingClient = new Client(username, encodedPassword);
        when(clientRepository.findAll()).thenReturn(List.of(existingClient));

        ClientRequest clientReq = new ClientRequest(username, password);

        assertThrows(UserAlreadyExistsException.class, () -> clientService.register(clientReq));
    }


    @Test
    public void testLoginAfterRegisteringUserWithValidCredentials(){
        String username = "testUser";
        String password = "testPassword";
        Client client = new Client(username, "encodedPassword");
        ClientRequest clientRequest = new ClientRequest(username, password);

        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        Client loggedIn = clientService.login(clientRequest);

        assertEquals(client, loggedIn);
    }

    @Test
    public void testLoginWithInvalidCredentials(){
        String username = "testUser";
        String password = "testPassword";
        ClientRequest clientRequest = new ClientRequest(username, password);

        when(clientRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> clientService.login(clientRequest));

    }


}

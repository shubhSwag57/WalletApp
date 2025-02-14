package com.example.walletApplication.services;

import com.example.walletApplication.Exceptions.InvalidCredentialsException;
import com.example.walletApplication.Exceptions.UserAlreadyExistsException;
import com.example.walletApplication.entity.Client;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.repository.ClientRepository;
import com.example.walletApplication.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private ClientRepository clientRepository;
    private WalletRepository walletRepository;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        walletRepository = mock(WalletRepository.class);
        clientService = new ClientService(clientRepository, walletRepository);
    }


    @Test
    public void testRegisterNewUserExpectRegisteredSuccessfully() {
        String username = "testUser";
        String password = "testPassword";
        Client client = new Client(username,password);
        clientService.register(client);
        verify(clientRepository).save(client);
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    public void testExistingUserExpectUserAlreadyExists() {
        String username = "testUser";
        String password = "testPassword";
        Client client = new Client(username, password);
        when(clientRepository.findAll()).thenReturn(List.of(client));

        assertThrows(UserAlreadyExistsException.class, () -> clientService.register(client));
    }

    @Test
    public void testLoginAfterRegisteringUserWithValidCredentials(){
        String username = "testUser";
        String password = "testPassword";
        Client client = new Client(username,password);
        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));

        Client loggedIn = clientService.login(username,password);

        assertEquals(client,loggedIn);
    }

    @Test
    public void testLoginWithInvalidCredentials(){
        String username = "testUser";
        String password = "testPassword";
        when(clientRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(InvalidCredentialsException.class, ()->clientService.login(username,password));

    }


}

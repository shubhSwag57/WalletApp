package com.example.walletApplication.entity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testUserCreation() {
        Client client = new Client("shubham", "xyz12345");
        assertEquals(client, new Client("shubham", "xyz12345"));
    }

    @Test
    public void testUserValidPasswordExpectTrue() {
        String rawPassword = "xyz12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Client client = new Client("shubham", encodedPassword);

        assertTrue(client.isValidPassword(rawPassword, passwordEncoder));
    }

    @Test
    public void testUserValidPasswordExpectFalse() {
        String rawPassword = "xyz12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Client client = new Client("shubham", encodedPassword);

        assertFalse(client.isValidPassword("wrongPassword", passwordEncoder));
    }

    @Test
    public void testUserSameUserNameExpectTrue() {
        Client client = new Client("shubham", "xyz12345");
        assertTrue(client.isSameUserName("shubham"));
    }

    @Test
    public void testUserSameUserNameExpectFalse() {
        Client client = new Client("shubham", "xyz12345");
        assertFalse(client.isSameUserName("wrongUsername"));
    }
}

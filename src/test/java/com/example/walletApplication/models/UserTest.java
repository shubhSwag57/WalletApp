package com.example.walletApplication.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testUserCreation(){
        User user = new User("shubham","xyz12345");
        assertEquals(user, new User("shubham","xyz12345"));
    }

    @Test
    public void testUserValidPasswordExpectTrue(){
        User user = new User("shubham","xyz12345");
        assertTrue(user.isValidPassword("xyz12345"));
    }
    @Test
    public void testUserValidPasswordExpectFalse(){
        User user = new User("shubham","xyz12345");
        assertFalse(user.isValidPassword("xyz123456"));
    }
}

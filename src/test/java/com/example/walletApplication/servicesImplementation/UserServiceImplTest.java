package com.example.walletApplication.servicesImplementation;

import com.example.walletApplication.messages.Messages;
import com.example.walletApplication.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    @Test
    public void testRegisterNewUserExpectRegisteredSuccessfully(){
        UserServiceImpl userService = new UserServiceImpl();
        String res = userService.register("test1","123455");
        assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,res);
    }

    @Test
    public void testRegisteredUser(){
        UserServiceImpl userService = new UserServiceImpl();
        String res = userService.register("test1","123455");
        assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,res);

        res = userService.register("test1","123455");
        assertEquals(Messages.USER_ALREADY_EXISTS,res);
    }

    @Test
    public void testAuthenticateValidUserExpectNotNull(){
        UserServiceImpl userService = new UserServiceImpl();
        String res = userService.register("test1","123455");
        assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,res);
        User user = userService.authenticate("test1","123455");
        assertNotNull(user);
    }

    @Test
    public void testAuthenticateInvalidUserExpectNoUser(){
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.authenticate("test1","123455");
        assertNull(user);

    }
}

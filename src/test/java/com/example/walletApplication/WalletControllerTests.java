package com.example.walletApplication;

import com.example.walletApplication.controller.WalletController;
import com.example.walletApplication.messages.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

public class WalletControllerTests {

	@Test
	public void testUserRegistration(){
		WalletController controller = new WalletController();
		assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,controller.register("shubham","xyz135"));
	}

	@Test
	public void testUserRegistrationExpectAlreadyExits(){
		WalletController controller = new WalletController();
		assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,controller.register("shubham","xyz135"));
		assertEquals(Messages.USER_ALREADY_EXISTS, controller.register("shubham","xyz135"));

	}

	@Test
	public void testWithdrawWithInvalidCredentials(){
		WalletController controller = new WalletController();
		assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,controller.register("shubham","xyz135"));
		assertEquals(Messages.INVALID_CREDENTIALS,controller.withdraw("shubham","xyz",100));
	}

	@Test
	public void testDepositWithInvalidCredentials(){
		WalletController controller = new WalletController();
		assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,controller.register("shubham","xyz135"));
		assertEquals(Messages.INVALID_CREDENTIALS,controller.deposit("shubham","xyz",100));

	}

	@Test
	public void testDepositEndPointWithValidCredentials(){
		WalletController controller = new WalletController();
		assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,controller.register("shubham","xyz135"));
		assertEquals("Deposited: 100.0, Total Balance: 100.0",controller.deposit("shubham","xyz135",100));
	}

	@Test
	public void testCheckBalanceForValidUser(){
		WalletController controller = new WalletController();
		assertEquals(Messages.USER_REGISTERED_SUCCESSFULLY,controller.register("shubham","xyz135"));
		assertEquals("Total Balance: 0.0",controller.getBalance("shubham","xyz135"));
		controller.deposit("shubham","xyz135",100);
		assertEquals("Total Balance: 100.0",controller.getBalance("shubham","xyz135"));

	}







}

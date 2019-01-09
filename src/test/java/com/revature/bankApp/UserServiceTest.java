package com.revature.bankApp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revature.bankModels.User;
import com.revature.service.NoSuchUserException;
import com.revature.service.UserService;

public class UserServiceTest {

	UserService us;
	@Test
	public void testGetUserService() {
		us = UserService.getUserService();
		assertTrue(us instanceof UserService);
		
	}
	@Test
	public void testGetUserByName()  {
		us = UserService.getUserService();
		User usr1 = us.getUserByName("mario");
		assertTrue(usr1.getUserName().equals("mario"));
		
	}
	@Test
	public void testGetAllUsers() {
		us = UserService.getUserService();
		List<User> ls = us.getAllUsers();
		assertTrue(ls instanceof List<?>);
		assertTrue(ls.size() >= 0);
	}
	@Test 
	public void testloginUser() {
		us = UserService.getUserService();
		User usr1 = us.loginUser("mario", "itsame!");
		assertEquals("mario", usr1.getUserName());
	}
	@Test 
	public void testNewUser() {
		us = UserService.getUserService();
		User usr1 = us.newUser("testUser", "helloworld", "Bowser", "Koopa");
		assertEquals("testUser", usr1.getUserName());
		assertTrue(usr1.getUserId() >= 0);
	}
	

}

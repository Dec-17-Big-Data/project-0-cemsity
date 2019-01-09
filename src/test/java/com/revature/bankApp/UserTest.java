package com.revature.bankApp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.bankModels.User;

public class UserTest {

	@Test
	public void Usertest() {
		User usr1 = new User(1,"jumpman","itsame!","mario","plumber");
		User usr2 = new User("jumpman","itsame!","mario","plumber");
		
		
		assertTrue(usr1.getUserId().equals(1));
		
		
		assertTrue(usr1.getUserName().equals("jumpman"));
		assertTrue(usr2.getUserName().equals("jumpman"));
		
		
		assertTrue(usr1.getUserPassword().equals("itsame!"));
		assertTrue(usr2.getUserPassword().equals("itsame!"));
		
		
		assertTrue(usr1.getUserFirstName().equals("mario"));
		assertTrue(usr2.getUserFirstName().equals("mario"));
		
		assertTrue(usr1.getUserLastName().equals("plumber"));
		assertTrue(usr1.getUserLastName().equals("plumber"));
		
		assertFalse(usr1.equals(usr2));
		assertTrue(usr1.equals(usr1));
		
	}

}

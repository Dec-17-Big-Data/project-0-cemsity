package com.revature.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.DAO.UserDao;
import com.revature.DAO.UserOracle;
import com.revature.bankModels.User;

public class UserService {
	private static UserService userService;
	private static Logger log = LogManager.getLogger(UserService.class);
	final static UserDao userDao = UserOracle.getDao();
	
	private UserService() {}
	
	public static UserService getUserService() {
		
		
		if (userService == null) {
			userService = new UserService();
		}
		return userService;
	}
	
	// Business Logic
	public User getUserByName(String userName){
		log.traceEntry();
		User user = null;
		Optional<User> optUser = userDao.getUserByName(userName);
		
		try {
			user = optUser.get();
			log.traceExit(user);
			return user;
		} catch (NoSuchElementException  e) {
			log.catching(e);
			log.error(e);
		}
		
		return null;
	}
	
	public User loginUser(String userName, String password) throws InvalidPasswordException {
		
		User user = this.getUserByName(userName);
		
		if (user == null) {
			this.signUpUser(userName, password);
		}
		if (user.getUserPassword() == password) {
			user.setLoggedIn(true);
		} else {
			throw new InvalidPasswordException();
		}
		
		return user;
		
	}
	
	public User signUpUser(String userName, String password) throws IllegalArgumentException{
		
		return null;
	}
	
	
}

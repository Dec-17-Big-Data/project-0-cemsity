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
		log.traceEntry();
		User user = this.getUserByName(userName);
		
		if (!user.getUserPassword().equals(password)) {
			throw new InvalidPasswordException();
		} 
		
		log.traceExit(user);
		return user;
		
	}
	public User logoutUser(User user) {
		user.setLoggedIn(false);
		return user;
	}
	
	public User newUser(String userName, String password, String firstName, String lastName) throws IllegalArgumentException{
		log.traceEntry();
		User user = new User(userName, password, firstName, lastName );
		boolean worked = userDao.newUser(user);
		if (worked) {
			user = this.getUserByName(userName);
			return user;
		}
		return null;
	}
	
	
}

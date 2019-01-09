package com.revature.service;

import java.util.List;
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
	public User getUserByName(String userName) throws NoSuchUserException  {
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
			throw new NoSuchUserException();
			
			
		} 
		
	}
	
	public User loginUser(String userName, String password) throws InvalidPasswordException, NoSuchUserException {
		log.traceEntry();
		
		User user;
		try {
			user= this.getUserByName(userName);
		} catch (NoSuchUserException e) {
			throw e;
		}
		if (!user.getUserPassword().equals(password)) {
			throw new InvalidPasswordException();
		} 
		
		log.traceExit(user);
		return user;
		
	}
		
	public User newUser(String userName, String password, String firstName, String lastName) throws UserNameExistsException, NoSuchUserException{
		log.traceEntry();
		User user = new User(userName, password, firstName, lastName );
		boolean worked = userDao.newUser(user);
		if (worked) {
			try {
				user = this.getUserByName(userName);
			} catch (NoSuchUserException e) {
				throw e;
			}
			log.traceExit(user);
			return user;
		} else {
			throw new UserNameExistsException();
			
		}
	}
	
	public List<User> getAllUsers() {
		log.traceEntry();
		
		Optional<List<User>> optList = userDao.getAllUsers();
		List<User> listUser = null;
		try {
			listUser = optList.get();
			log.traceExit(listUser);
			return listUser;
		} catch (Exception e){
			log.catching(e);
			
			
		}
		
		return null;
	};
	
	public User updateUser(User user) throws NoSuchUserException{
		log.traceEntry();
		boolean worked = userDao.updateUser(user);
		if (worked) {
			try {
				user = this.getUserByName(user.getUserName());
			} catch (NoSuchUserException e) {
				throw e;
			}
			log.traceExit(user);
			return user;
		} 
		return null;
	}
	
	public boolean deleteUser(User user) {
		log.traceEntry();
		boolean worked = userDao.deleteUser(user);
		if (worked) {
			log.traceExit(worked);
			return worked;
		}
		return false;
	}
	
	
}

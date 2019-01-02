package com.revature.DAO;

import java.util.List;
import java.util.Optional;

import com.revature.bankModels.User;

public interface UserDao {
	Optional<List<User>> getAllUsers();
	Optional<User> getUserByName(String userName);
	Optional<User> getUserById(Integer userId);
	boolean newUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(User user);
	
}

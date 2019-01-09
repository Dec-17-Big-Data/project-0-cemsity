package com.revature.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankApp.ConnectionUtil;
import com.revature.bankModels.User;

public class UserOracle implements UserDao{
	private static UserOracle userOracle;
	private Logger log = LogManager.getLogger(UserOracle.class);
	
	private UserOracle() {}
	public static UserDao getDao() {
		if (userOracle == null) {
			userOracle = new UserOracle();
		}
		return userOracle;
	} 
	
	@Override
	public Optional<List<User>> getAllUsers() {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from users";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<User> listOfUsers = new ArrayList<User>();
			
			while(rs.next()) {
				listOfUsers.add(new User(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"),
						rs.getString("user_first"),
						rs.getString("user_last")
						
						));
			}
			log.traceExit(Optional.of(listOfUsers));
			return Optional.of(listOfUsers);
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(null);
		return null;
	}

	@Override
	public Optional<User> getUserByName(String userName) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from users where user_name = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			User user = null;
			
			while(rs.next()) {
				user = new User(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"),
						rs.getString("user_first"),
						rs.getString("user_last")
						);
			}
			log.traceExit(Optional.of(user));
			return Optional.of(user);
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(null);
		return null;
	}
	

	@Override
	public Optional<User> getUserById(Integer userId) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from users where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			User user = null;
			
			while(rs.next()) {
				user = new User(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"),
						rs.getString("user_first"),
						rs.getString("user_last")
						);
			}
			log.traceExit(Optional.of(user));
			return Optional.of(user);
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(null);
		return null;
	}
	public boolean newUser(User user) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		try {
			String call = "CALL new_user(?,?,?,?)";
			CallableStatement cs = con.prepareCall(call);
			cs.setString(1, user.getUserName());
			cs.setString(2, user.getUserFirstName());
			cs.setString(3, user.getUserLastName());
			cs.setString(4, user.getUserPassword());
			
			cs.execute();
			
			log.traceExit(true);
			return true;
			
		} catch (Exception e) {
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}
	public boolean updateUser(User user) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		try {
			String call = "CALL update_user(?,?,?,?,?)";
			CallableStatement cs = con.prepareCall(call);
			cs.setString(1, Integer.toString(user.getUserId()));
			cs.setString(2, user.getUserName());
			cs.setString(3, user.getUserFirstName());
			cs.setString(4, user.getUserLastName());
			cs.setString(5, user.getUserPassword());
			
			cs.execute();
			log.traceExit(true);
			return true;
			
			
		} catch (Exception e) {
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}
	public boolean deleteUser(User user) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		try {
			String call = "CALL delete_user(?)";
			CallableStatement cs = con.prepareCall(call);
			cs.setLong(1, user.getUserId());
			
			cs.execute();
			log.traceExit(true);
			return true;
			
			
		} catch (Exception e) {
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}
}

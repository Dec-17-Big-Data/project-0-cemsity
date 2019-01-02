package com.revature.DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.revature.bankApp.ConnectionUtil;
import com.revature.bankModels.Account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class AccountOracle implements AccountDao{
	
	private static AccountOracle accountOracle;
	private static Logger log = LogManager.getLogger(AccountOracle.class);
	
	private AccountOracle() {} // private Constructor for singleton 
	
	public static AccountDao getDao() {
		if (accountOracle == null) {
			accountOracle = new AccountOracle();
		}
		return accountOracle;
	}
	
	@Override
	public Optional<List<Account>> getAllAccounts() {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from accounts";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<Account> listOfAccount = new ArrayList<Account>();
			
			while(rs.next()) {
				listOfAccount.add(new Account(rs.getInt("account_id"),
						rs.getInt("user_id"),
						rs.getString("account_type"),
						rs.getInt("account_ammount")));
			}
			log.traceExit(Optional.of(listOfAccount));
			return Optional.of(listOfAccount);
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(null);
		return null;
	}		
	@Override
	public Optional<Account> getAccountById(Integer accountID){
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from accounts where account_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, accountID);
			ResultSet rs = ps.executeQuery();
			
			Account account = null;
			
			while(rs.next()) {
				account = new Account(rs.getInt("account_id"),
						rs.getInt("user_id"),
						rs.getString("account_type"),
						rs.getInt("account_ammount"));
			}
			log.traceExit(Optional.of(account));
			return Optional.of(account);
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(null);
		return null;
	}
	@Override
	public Optional<List<Account>> getAccountsByUser(Integer userID) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from accounts where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			List<Account> listOfAccount = new ArrayList<Account>();
			
			while(rs.next()) {
				listOfAccount.add(new Account(rs.getInt("account_id"),
						rs.getInt("user_id"),
						rs.getString("account_type"),
						rs.getInt("account_ammount")));
			}
			log.traceExit(Optional.of(listOfAccount));
			return Optional.of(listOfAccount);
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(null);
		return null;
	}

	@Override
	public boolean newAccount(Account account) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		
		try {
			String sql = "call new_account(?, ?)";
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setInt(1, account.getUserID());
			cs.setString(2, account.getType());
			
			boolean result = cs.execute();
			
			log.traceExit(result);
			return result;
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}

	@Override
	public boolean deposit(Account account, Integer ammount) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		
		try {
			String sql = "call deposit(?, ?)";
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setInt(1, account.getAccountId());
			cs.setInt(2, ammount);
			
			boolean result = cs.execute();
			
			log.traceExit(result);
			return result;
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}

	@Override
	public boolean withdraw(Account account, Integer ammount) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		
		try {
			String sql = "call withdraw(?, ?)";
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setInt(1, account.getAccountId());
			cs.setInt(2, ammount);
			
			boolean result = cs.execute();
			
			log.traceExit(result);
			return result;
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}

	@Override
	public boolean transfer(Account accountWithdraw, Account accountDeposit, Integer ammount) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(false);
			return false;
		}
		
		try {
			String sql = "call transfer(?, ?, ?)";
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setInt(1, accountWithdraw.getAccountId());
			cs.setInt(2, accountDeposit.getAccountId());
			cs.setInt(3, ammount);
			
			boolean result = cs.execute();
			
			log.traceExit(result);
			return result;
		} catch (Exception e){
			log.catching(e);
			log.error("SQL Exception occured.", e);
		}
		log.traceExit(false);
		return false;
	}

	@Override
	public boolean deleteAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	
	 

}

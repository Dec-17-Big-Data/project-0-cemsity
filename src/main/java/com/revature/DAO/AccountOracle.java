package com.revature.DAO;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankModels.Account;

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
	
	
	
	public Optional<List<Account>> getAllAccount() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Optional<List<Account>> getAccountsByUser(Integer userID){
		return null;
	}
	public Optional<Account> getAccountById(Integer AccountId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 

}

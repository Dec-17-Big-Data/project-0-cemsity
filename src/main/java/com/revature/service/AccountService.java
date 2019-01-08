package com.revature.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.DAO.*;
import com.revature.bankModels.Account;


public class AccountService {
	private static AccountService accountService;
	private static Logger log = LogManager.getLogger(AccountService.class);
	final static AccountDao accountDao = AccountOracle.getDao();
	
	private AccountService() {}
	
	public static AccountService getAccountService() {
		if (accountService == null) {
			accountService = new AccountService();
		}
		return accountService;
	}
	
	public List<Account> getAccountsbyUserID(Integer userId ){
		log.traceEntry();
		List<Account> listAcc = null;
		Optional<List<Account>> optList = accountDao.getAccountsByUser(userId);
		
		try {
			listAcc = optList.get();
			log.traceExit(listAcc);
			return listAcc;
			
		} catch (Exception e) {
			
		}
		
		return null;
	}
	
	public Account getAccountById(Integer accountID) {
		log.traceEntry();
		Account account = null;
		Optional<Account> optAcc = accountDao.getAccountById(accountID);
		
		try {
			account = optAcc.get();
			log.traceExit(account);
			return account;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public boolean newAccount(Integer userId, String type) {
		log.traceEntry();
		Account newAcc = new Account();
		newAcc.setUserID(userId);
		newAcc.setType(type);
		
		boolean worked = accountDao.newAccount(newAcc);
		log.traceExit(worked);
		return worked;
		
	}
	
	public boolean deposit(Account account, Integer ammount) {
		log.traceEntry();
		boolean worked = false;
		if (ammount >= 0) { 
			worked = accountDao.deposit(account, ammount);
		}
		log.traceExit(worked);
		return worked;
	}
	public boolean withdraw(Account account, Integer ammount) throws InsufficientBalanceException {
		Integer accAmt = account.getAmount();
		boolean worked = false;
		if (accAmt < ammount) {
			worked = accountDao.withdraw(account, ammount);	
		} else {
			throw new InsufficientBalanceException();
		}
		
		return worked;
	}
	public boolean transfer(Account account1, Account account2, Integer ammount)  throws InsufficientBalanceException{
		Integer source = account1.getAmount();
		boolean worked = false;
		
		if(source < ammount) {
			worked = accountDao.transfer(account1, account2, ammount);
		} else {
			throw new InsufficientBalanceException();
		}
		return worked;
	}
	
	public boolean deleteAccount(Account account) throws BalanceException{
		Integer accVal = account.getAmount();
		boolean worked = false;
		if (accVal == 0) {
			worked = deleteAccount(account);
		} else {
			throw new BalanceException();
		}
		return worked;
	}
}

package com.revature.bankApp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revature.bankModels.Account;
import com.revature.service.AccountService;

public class AccountServiceTest {
	
	AccountService as; 
	@Test 
	public void testGetAccountService() {
		as = AccountService.getAccountService();
		
		assert( as instanceof AccountService);
	}
	@Test
	public void testGetAccountsbyUserID() {
		as = AccountService.getAccountService();
		
		List<Account> ls1 = as.getAccountsbyUserID(1);
		assertTrue(ls1 instanceof List<?>);
		assertTrue(ls1.size() >= 0);
		
	}
	@Test
	public void testGetAccountbyId() {
		as = AccountService.getAccountService();
		Account acc = as.getAccountById(1);
		assertTrue(acc.getAccountId() == 1);
		assertTrue(acc.getAmount() >= 0);
		assertTrue(acc.getUserID() == 1);
		
		
	}
	@Test
	public void testNewAccount() {
		as = AccountService.getAccountService();
		assertTrue(as.newAccount(1, "test"));
	}
	@Test 
	public void testDeposit() {
		as = AccountService.getAccountService();
		Account acc = as.getAccountById(1);
		assertTrue(as.deposit(acc, 10));
	}
	
	

}

package com.revature.DAO;

import java.util.List;
import java.util.Optional;

import com.revature.bankModels.Account;

public interface AccountDao {
	Optional<List<Account>> getAllAccounts();
	Optional<Account> getAccountById(Integer AccountId);
	Optional<List<Account>> getAccountsByUser(Integer userID);
	boolean newAccount(Account account);
	boolean deposit(Account account, Integer ammount);
	boolean withdraw(Account account, Integer ammount);
	boolean transfer(Account accountWithdraw, Account accountDeposit, Integer ammount);
	boolean deleteAccount(Account account);
}

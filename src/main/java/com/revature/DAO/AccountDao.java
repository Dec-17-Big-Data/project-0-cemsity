package com.revature.DAO;

import java.util.List;
import java.util.Optional;

import com.revature.bankModels.Account;

public interface AccountDao {
	Optional<List<Account>> getAllAccount();
	Optional<Account> getAccountById(Integer AccountId);
}

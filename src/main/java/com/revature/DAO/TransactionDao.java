package com.revature.DAO;

import java.util.List;
import java.util.Optional;

import com.revature.bankModels.Transaction;

public interface TransactionDao {
	Optional<List<Transaction>> getAllTransactions();
	Optional<Transaction> getTransactionById(Integer transactionId);

}
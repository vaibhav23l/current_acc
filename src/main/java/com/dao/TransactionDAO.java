package com.dao;

import java.util.List;

import com.exception.BankException;
import com.model.AccountTransaction;
import com.model.AccountTransaction.TransactionType;


public interface TransactionDAO {
	public int addTransaction(int accountId, double amount, TransactionType transactionType) throws BankException ;


	List<AccountTransaction> findAllTransAccId(int accountId) throws BankException ;
}

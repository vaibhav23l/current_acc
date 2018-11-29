package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TransactionDAO;
import com.exception.BankException;
import com.model.AccountTransaction;
import com.model.AccountTransaction.TransactionType;

@Service
public class TransactionService {
	@Autowired
	TransactionDAO transactionDao;
	
	public int addTransaction(int accountId, double amount, TransactionType transactionType) throws BankException {
		return transactionDao.addTransaction(accountId, amount, transactionType);
	}
	
	public List<AccountTransaction> findAllTransaction(int accountId) throws BankException {
		return transactionDao.findAllTransAccId(accountId);
	}
}

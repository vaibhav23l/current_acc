package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AccountDAO;
import com.exception.BankException;
import com.model.Account;
import com.model.Account.AccountType;
import com.model.AccountTransaction.TransactionType;
import com.model.Customer;

@Service
public class AccountService {

	@Autowired
	AccountDAO accountDao;
	
	@Autowired
	TransactionService transService;
	
	public Map<String, Integer> addCurrentAccount(Customer customer) throws BankException {
		if (customer == null || customer.getCustomerId() == 0) {
			return null;
		}
		
		int res = accountDao.addAccount(customer, AccountType.CURRENT);
		
		Map<String, Integer> accRes = new HashMap<>();
		accRes.put("accountNumber", res);
		
		if (customer.getBalance() > 0) {
			transService.addTransaction(res, customer.getBalance(), TransactionType.CREDIT);
		}
		return accRes;
	}
	
	public List<Account> listCustomerAccount(int customerId) throws BankException {
		
		
		List<Account> res = accountDao.findByCustomerId(customerId);
		
		
		return res;
	}
}

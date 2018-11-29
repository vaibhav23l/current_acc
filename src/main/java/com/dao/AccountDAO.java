package com.dao;

import java.util.List;

import com.exception.BankException;
import com.model.Account;
import com.model.Customer;

public interface AccountDAO {
	public int addAccount(Customer customer, Account.AccountType accountType) throws BankException ;
	public List<Account> findByCustomerId(int accountId) throws BankException ;
}

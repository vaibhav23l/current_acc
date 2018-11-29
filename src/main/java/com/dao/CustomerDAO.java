package com.dao;

import java.util.List;

import com.exception.BankException;
import com.model.Customer;

public interface CustomerDAO {

	int addCustomer (Customer customer) throws BankException ;
	
	Customer findById (int customerId) throws BankException ;
	
	Customer findByName(String name) throws BankException ;
	
	List<Customer> findAll() throws BankException ;
}

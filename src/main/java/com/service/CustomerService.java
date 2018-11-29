package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dao.CustomerDAO;
import com.exception.BankException;
import com.model.Customer;

@Service
@Repository
public class CustomerService {

	@Autowired
	CustomerDAO customerDAO;
	
	public Customer getCustomer(int customerId) throws BankException {
		return customerDAO.findById(customerId);
	}
	
	public List<Customer> findAllCustomer() throws BankException {
		return customerDAO.findAll();
	}
	
	public int addCustomer(Customer customer) throws BankException {
		return customerDAO.addCustomer(customer);
	}
}

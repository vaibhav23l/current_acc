package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exception.BankException;
import com.model.Account;
import com.model.AccountTransaction;
import com.model.Customer;
import com.service.AccountService;
import com.service.CustomerService;
import com.service.RestObjectConvertor;
import com.service.TransactionService;

@RestController
public class ApiController {

	@Autowired
	CustomerService customerService;

	@Autowired
	AccountService accountService;

	@Autowired
	TransactionService transService;

	@Autowired
	RestObjectConvertor convertor;

	@RequestMapping(path = "get_customer/{customerId}", method = RequestMethod.GET)
	@ResponseBody
	public String getCustomer(@PathVariable int customerId)
			throws BankException {

		Customer customer = customerService.getCustomer(customerId);

		String result = convertor.convertToJson(customer);
		System.out.println("Customer : " + result);
		return result;

	}

	@RequestMapping(path = "add_customer", method = RequestMethod.POST)
	@ResponseBody
	public String addCustomer(@RequestBody Customer customer)
			throws BankException {

		int i = customerService.addCustomer(customer);
		customer.setCustomerId(i);

		String result = convertor.convertToJson(customer);
		System.out.println("Customer : " + result);
		return result;

	}

	@RequestMapping(path = "get_all_customer", method = RequestMethod.GET)
	@ResponseBody
	public String getAllCustomers() throws BankException {

		List<Customer> customer = customerService.findAllCustomer();

		String result = convertor.convertToJson(customer);
		System.out.println("Customer : " + result);
		return result;

	}

	@RequestMapping(path = "add_current_account", method = RequestMethod.POST, consumes = "application/json")
	public String addCurrentAccount(@RequestBody Customer customer)
			throws BankException {
		Map<String, Integer> res = accountService.addCurrentAccount(customer);
		return convertor.convertToJson(res);

	}

	@RequestMapping(path = "list_customer_account/{customerId}", method = RequestMethod.GET, consumes = "application/json")
	public String listCustomerAccounts(@PathVariable int customerId)
			throws BankException {
		List<Account> res = accountService.listCustomerAccount(customerId);
		return convertor.convertToJson(res);

	}

	@RequestMapping(path = "find_all_trans_accid/{accountId}", method = RequestMethod.GET)
	@ResponseBody
	public String findTransactionForAccount(@PathVariable int accountId)
			throws BankException {
		List<AccountTransaction> res = transService
				.findAllTransaction(accountId);
		return convertor.convertToJson(res);

	}

	@RequestMapping(path = "add_trans_accid", method = RequestMethod.POST)
	@ResponseBody
	public String addTransactionForAccount(
			@RequestBody AccountTransaction accountTransaction)
			throws BankException {
		
		if (accountTransaction.getAmount() < 0) {
			throw new BankException("Amount cannot be less than 0");
		}
		int res = transService.addTransaction(
				accountTransaction.getAccountId(),
				accountTransaction.getAmount(),
				accountTransaction.getTransactionType());
		return convertor.convertToJson(res);

	}

}

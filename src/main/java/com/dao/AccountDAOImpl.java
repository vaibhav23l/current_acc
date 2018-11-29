package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exception.BankException;
import com.model.Account;
import com.model.Account.AccountType;
import com.model.Customer;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	CustomerDAO customerDao;
	
	@Autowired
	SequenceGenerator generator;

	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		generator = new SequenceGenerator();
		generator.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
		
	}

	@Override
	public int addAccount(Customer customer, AccountType accountType) throws BankException {
		Customer existingCustomer = customerDao.findById(customer.getCustomerId());
		if (existingCustomer == null) {
			throw new BankException("Customer Not Found");
		}
		
		
		int nextKey = generator.getNextSequence("ACCOUNT");
		Map<String, Object> params = new HashMap<>();
		System.out.println("nextKey : " + nextKey);
		params.put("account_id", nextKey);
		params.put("customer_id", customer.getCustomerId());
		params.put("account_type", accountType.name());
		params.put("create_date", new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));

		String sql = "INSERT INTO ACCOUNT VALUES (:account_id, :customer_id, :account_type, :create_date)";

		try {
			namedParameterJdbcTemplate.update(sql, params);
		} catch (Exception e) { 
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}
		return nextKey;
	}

	@Override
	public List<Account> findByCustomerId(int customerId) throws BankException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customerId);

		String sql = "select a.account_id, a.account_type, (SUM(b.credit) - SUM(b.debit)) as balance from ACCOUNT a"
						+ " LEFT JOIN ACC_TRANSACTION b "
						+ " ON a.account_id=b.account_id"
						+ " WHERE a.customer_id=:customer_id GROUP BY a.account_id, a.account_type"; 
		
		List<Account> result = new ArrayList<>();
		try {

			result = namedParameterJdbcTemplate.query(sql, params,
				new AccountBalanceMapper());
		} catch (Exception e ) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}

		return result;
	}

	private static final class AccountBalanceMapper implements RowMapper<Account> {

		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account();
			account.setAccountId(rs.getInt("account_id"));
			account.setBalance(rs.getDouble("balance"));
			account.setAccountType(AccountType.valueOf(rs
					.getString("account_type")));
			return account;
		}
	}

}

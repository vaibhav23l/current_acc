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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exception.BankException;
import com.model.AccountTransaction;
import com.model.AccountTransaction.TransactionType;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	SequenceGenerator generator;

	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		generator = new SequenceGenerator();
		generator.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
	}


	private static final class AccountMapper implements RowMapper<AccountTransaction> {

		public AccountTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountTransaction transaction = new AccountTransaction();
			transaction.setTransactionId(rs.getInt("transaction_id"));
			transaction.setAccountId(rs.getInt("account_id"));
			transaction.setCredit(rs.getDouble("credit"));
			transaction.setDebit(rs.getDouble("debit"));
			transaction.setCreateDate(rs.getTimestamp("create_date"));
			return transaction;
		}
	}

	@Override
	public int addTransaction(int accountId, double amount,
			TransactionType transactionType) throws BankException {
		int nextKey = generator.getNextSequence("TRANSACTION");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("transaction_id", nextKey);
		params.addValue("account_id", accountId);
		
		if(TransactionType.CREDIT.equals(transactionType)) {
			params.addValue("credit", amount);
			params.addValue("debit", 0);
		} else if(TransactionType.DEBIT.equals(transactionType)) {
			params.addValue("debit", amount);
			params.addValue("credit", 0);
		}
		params.addValue("create_date", new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));

		String sql = "INSERT INTO ACC_TRANSACTION (transaction_id, account_id, debit, credit, create_date) VALUES (:transaction_id, :account_id, :debit, :credit, :create_date)";

		try {
			namedParameterJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}
		return nextKey;
	}

	@Override
	public List<AccountTransaction> findAllTransAccId(int accountId) throws BankException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account_id", accountId);
		String sql = "SELECT * FROM ACC_TRANSACTION WHERE account_id=:account_id";
		List<AccountTransaction> result = new ArrayList<>();
		try {
			result = namedParameterJdbcTemplate.query(sql, params,
					new AccountMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}
		

		return result;

	}
}

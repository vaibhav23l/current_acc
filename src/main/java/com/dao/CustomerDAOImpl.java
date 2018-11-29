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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.exception.BankException;
import com.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

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

	@Override
	public int addCustomer(Customer customer) throws BankException {

		int nextKey = generator.getNextSequence("CUSTOMER");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("customer_id", nextKey);
		params.addValue("first_name", customer.getFirstName());
		params.addValue("last_name", customer.getLastName());

		String sql = "INSERT INTO CUSTOMER  VALUES (:customer_id, :first_name, :last_name)";
		try {
			namedParameterJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}

		return nextKey;
	}

	@Override
	public Customer findById(int customerId) throws BankException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customerId);

		String sql = "SELECT * FROM CUSTOMER WHERE customer_id=:customer_id";

		Customer result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql,
				params, new CustomerMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}


		return result;
	}

	@Override
	public Customer findByName(String name) throws BankException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);

		String sql = "SELECT * FROM CUSTOMER WHERE first_name=:name";
		Customer result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql,
				params, new CustomerMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}

		return result;

	}

	@Override
	public List<Customer> findAll() throws BankException {

		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT * FROM CUSTOMER";
		List<Customer> result = new ArrayList<Customer>();
		try {
			result = namedParameterJdbcTemplate.query(sql, params,
			new CustomerMapper());
		} catch(Exception e) {
			e.printStackTrace();
			throw new BankException("Something Broke! Please try after sometime");
		}

		return result;

	}

	private static final class CustomerMapper implements RowMapper<Customer> {

		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setCustomerId(rs.getInt("customer_id"));
			customer.setFirstName(rs.getString("first_name"));
			customer.setLastName(rs.getString("last_name"));
			return customer;
		}
	}

}

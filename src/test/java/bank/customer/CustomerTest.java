package bank.customer;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.dao.AccountDAOImpl;
import com.dao.CustomerDAO;
import com.dao.CustomerDAOImpl;
import com.dao.SequenceGenerator;
import com.exception.BankException;
import com.model.Account;
import com.model.Account.AccountType;
import com.model.Customer;

public class CustomerTest {
	private EmbeddedDatabase db;

	CustomerDAO userDao;

	@Before
	public void setUp() {
		db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY)
				.setName("bank_acc").addScript("db/create_db.sql")
				.addScript("db/insert_data.sql").build();
	}

	@Test
	public void testAddCustomer() throws BankException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		CustomerDAOImpl customerDao = new CustomerDAOImpl();
		customerDao.setNamedParameterJdbcTemplate(template);
		Customer c = new Customer();
		c.setFirstName("Mike");
		c.setLastName("John");
		customerDao.addCustomer(c);
	}

	@Test
	public void testFindCustomerId() throws BankException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		CustomerDAOImpl customerDao = new CustomerDAOImpl();
		customerDao.setNamedParameterJdbcTemplate(template);

		Customer user = customerDao.findById(1);
		System.out.println("Account " + user);
		Assert.assertNotNull(user);

	}
	
	@Test
	public void testFindByname() throws BankException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		CustomerDAOImpl customerDao = new CustomerDAOImpl();
		customerDao.setNamedParameterJdbcTemplate(template);

		Customer user = customerDao.findByName("John");
		System.out.println("user " + user);
		Assert.assertNotNull(user);

	}
	
	@Test
	public void testFindAccountId() throws BankException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		AccountDAOImpl accountDAO = new AccountDAOImpl();
		accountDAO.setNamedParameterJdbcTemplate(template);

		List<Account> user = accountDAO.findByCustomerId(1);
		System.out.println("Account " + user);
		Assert.assertNotNull(user);

	}

	

	@Test
	public void testFindAllCustomer() throws BankException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		CustomerDAOImpl customerDao = new CustomerDAOImpl();
		customerDao.setNamedParameterJdbcTemplate(template);
		Assert.assertNotNull(customerDao.findAll());
	}

	@Test
	public void testAddCurrentAcc() throws BankException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		AccountDAOImpl accountDAO = new AccountDAOImpl();
		accountDAO.setNamedParameterJdbcTemplate(template);

		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setBalance(0);
		int res = accountDAO.addAccount(customer, AccountType.CURRENT);

		Assert.assertNotEquals(res, 0);
	}

	@Test
	public void testSequence() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		SequenceGenerator accountDAO = new SequenceGenerator();
		accountDAO.setNamedParameterJdbcTemplate(template);

		int user = accountDAO.getNextSequence("CUSTOMER");
		Assert.assertNotEquals(user, 0);
	}

	@After
	public void tearDown() {
		db.shutdown();
	}
}

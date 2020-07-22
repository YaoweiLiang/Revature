package dev.yaowei.DAOtests;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dev.yaowei.daos.AccountDAO;
import dev.yaowei.daos.AccountDAOImpl;
import dev.yaowei.daos.CustomerDAO;
import dev.yaowei.daos.CustomerDAOImpl;
import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
import dev.yaowei.exceptions.NegativeBalanceExceptions;
import dev.yaowei.utils.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class)
public class AccountDAOtests {
	private static AccountDAO adao = AccountDAOImpl.getAccountDAOImpl(); 
	public static  CustomerDAO cdao =  CustomerDAOImpl.getCustomerDAOImpl();
	
	// @BeforeAll
	@BeforeEach
	public void setUp() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "CALL projectzero_db.set_up_bankdb";
			CallableStatement cs = conn.prepareCall(sql);
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void createCustomer() {
		Customer yao = Customer.create("username", "password");
		cdao.createCustomer(yao);
		Assertions.assertNotEquals(0, yao.getcId());
		Assertions.assertEquals(1, yao.getcId());
	}
	
	@Test
	@Order(2)
	void createAccount() throws NegativeBalanceExceptions {
		tearDown();
		setUp();
		createCustomer();
		Account tim2 = new Account(0,1,"Tim",1000);
		adao.createAccounts(tim2);
		int id = tim2.getaId();
		System.out.println(id);
		Assertions.assertNotEquals(0, id);
		Assertions.assertEquals(1, id);
	}
	
	@Test
	void getAccountById() throws NegativeBalanceExceptions {
		tearDown();
		setUp();
		createAccount();
		Account tim2 = adao.getAccountById(1);
		Assertions.assertNotNull(tim2);
		Assertions.assertEquals(1, tim2.getaId());
	}

	@Test
	void getAccountsByCId() throws NegativeBalanceExceptions {		
		tearDown();
		setUp();
		int customerId = 1;
		createCustomer();
		adao.createAccounts(Account.create(customerId, "Tim01", 1000));
		adao.createAccounts(Account.create(customerId, "Tim02", 1000));
		Set<Account> accounts = adao.getAccountsByCId(1);
		Assertions.assertEquals(2, accounts.size());		
	}
	

	
	@Test
	public void deleteAccount() throws NegativeBalanceExceptions {
		createCustomer();
		Account tim2 = new Account(0,1,"Tim",1000);
		adao.createAccounts(tim2);
		int accountId = tim2.getaId();

		Account newAccount = adao.getAccountById(accountId);
		Assertions.assertNotEquals(null, newAccount);
		
		boolean result = adao.deleteAccountById(accountId);
		Assertions.assertEquals(true, result);
		
		//testing if it is already deleted
		Account resultAccount = adao.getAccountById(accountId);
		Assertions.assertEquals(null, resultAccount);
	}
	// @AfterAll
	@AfterEach
	public void tearDown() {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "CALL tear_down_bankdb";
			CallableStatement cs = conn.prepareCall(sql);
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
	
	
	
	/*	//testing
	@Test
	public void getAllAccounts() throws NegativeBalanceExceptions {
		tearDown();
		setUp();
		createCustomer();
		adao.createAccounts(Account.create("Tim01", 1000));
		adao.createAccounts(Account.create("Tim02", 1000));
		Set<Account> accounts = adao.getAllAccounts();
		
		Assertions.assertEquals(2, accounts.size());
	}*/

}

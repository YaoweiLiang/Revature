package dev.yaowei.DAOtests;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.sun.tools.javac.util.List;

import dev.yaowei.daos.AccountDAO;
import dev.yaowei.daos.AccountDAOImpl;
import dev.yaowei.daos.CustomerDAO;
import dev.yaowei.daos.CustomerDAOImpl;
import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
import dev.yaowei.exceptions.NegativeBalanceExceptions;
import dev.yaowei.utils.ConnectionUtil;

public class CustomerDAOtests {
	private static CustomerDAO cdao =  CustomerDAOImpl.getCustomerDAOImpl();
	
	private void setUp() {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "CALL projectzero_db.set_up_bankdb";
			CallableStatement cs = conn.prepareCall(sql);
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void cleanUp() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "CALL projectzero_db.tear_down_bankdb";
			CallableStatement cs = conn.prepareCall(sql);
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createCustomer() {
		cleanUp();
		setUp();
		Customer customer = Customer.create("Username", "Password23");	
		cdao.createCustomer(customer);
		Assertions.assertNotEquals(0, customer.getcId());
		Assertions.assertEquals(1, customer.getcId());
	}
	
	@Test
	public void getCustomerById() {
		cleanUp();
		setUp();
		createCustomer();
		Customer yao = cdao.getCustomerBycId(1);
		Assertions.assertEquals(1,  yao.getcId());
	}

	
	private static AccountDAO adao = AccountDAOImpl.getAccountDAOImpl();

	@Test
	public void getAllCustomers() {
		cleanUp();
		setUp();
		
		cdao.createCustomer(Customer.create("Username", "Password23"));
		cdao.createCustomer(Customer.create("Username2", "Password2"));
		Set<Customer> customers = cdao.getAllCustomer();
		
		Assertions.assertEquals(2, customers.size());
	}
	
	// Problem: accounts were not inserted.
	@Test
	public void getAllCustomersWithAccounts() throws NegativeBalanceExceptions {
		cleanUp();
		setUp();
		int customerId = 1;
		cdao.createCustomer(Customer.create("Username", "Password23"));
		cdao.createCustomer(Customer.create("Username2", "Password2"));
		
		adao.createAccounts(Account.create(customerId, "joh", 0));
		adao.createAccounts(Account.create(customerId, "joh2", 0));

		Set<Customer> customers = cdao.getAllCustomer();
		System.out.println(customers+"set");
	
		Assertions.assertEquals(2, customers.size());
		Assertions.assertEquals(2, getCustomerByCustomerId(customers, customerId).getCustomerAccounts().size());
		Assertions.assertEquals(0, getCustomerByCustomerId(customers,  2).getCustomerAccounts().size());
	}
	
	private Customer getCustomerByCustomerId(Set<Customer> customers, int id) {
		cleanUp();
		setUp();
		Customer[] result = new Customer[customers.size()];
		customers.toArray(result);
		for(Customer item : result) {
			if (item.getcId() == id) {
				return item;
			}
		}
		
		return null;
	}

	@Test
	public void checkArrayConversion() {
		Set<Customer> customers = new HashSet<Customer>();
		customers.add(Customer.create("user", "password"));
		ArrayList<Customer> list = toArrayList(customers);
		String name = list.get(0).getUsername();
		Assertions.assertEquals("user", name);
	}
	
	@Test
	public void deleteCustomer() {
		cleanUp();
		setUp();
		// insert customer
		Customer customer = Customer.create("Username", "Password23");	
		cdao.createCustomer(customer);
		int customerId = customer.getcId();
		
		// find customerc
		Customer newCustomer = cdao.getCustomerBycId(customerId);
		Assertions.assertNotEquals(null, newCustomer);
		
		// delete customer
		boolean result = cdao.deleteCustomer(customerId);
		Assertions.assertEquals(true, result);
		/*
		Customer resultCustomer = cdao.getCustomerBycId(customerId);
		Assertions.assertEquals(null, resultCustomer);
		*/
	}

	private <T> ArrayList<T> toArrayList(Set<T> set) {
		ArrayList<T> result = new ArrayList<T>();
		Iterator<T> iter = set.iterator();
		while(iter.hasNext()) {
			result.add(iter.next());
		}
		
		return result;
	}
	
}

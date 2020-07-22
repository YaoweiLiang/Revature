package dev.yaowei.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dev.yaowei.daos.AccountDAO;
import dev.yaowei.daos.AccountDAOImpl;
import dev.yaowei.daos.CustomerDAO;
import dev.yaowei.daos.CustomerDAOImpl;
import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;

public class CustomerServiceImpl implements CustomerService {
	private static CustomerDAO cdao = CustomerDAOImpl.getCustomerDAOImpl(); // we already have our basic CRUD operations
	private static AccountDAO adao = AccountDAOImpl.getAccountDAOImpl();
	
	@Override
	public Customer establishCustomer(Customer customer) {
		return cdao.createCustomer(customer);
	}

	@Override
	public Customer getCustomerById(int id) {
		Customer customer = cdao.getCustomerBycId(id);
		Set<Account> accounts = adao.getAccountsByCId(id);
		customer.setCustomerAccounts(accounts);
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return cdao.updateCustomer(customer);
	}

	@Override
	public boolean deleteCustomerById(int id) {
		return cdao.deleteCustomer(id);
	}

	@Override
	public Set<Customer> getAllCustomers() {
		return cdao.getAllCustomer();
	}

	@Override
	public Customer renameCustomer(Customer customer, String newUsername) {
		customer.setUsername(newUsername);
		cdao.updateCustomer(customer);
		return customer;
	}
	
	@Override
	public Set<Customer> getCustomersByAccountBalanceLessThan(int num) {
		
		Set<Customer> result = new HashSet<Customer>();
		
		for(Customer customer : cdao.getAllCustomer()) {
			Set<Account> accountSet = customer.getCustomerAccounts();
			ArrayList<Account> accounts = toArrayList(accountSet);
			for(Account account : accounts) {
				if (account.getBalance() < num) {
					result.add(customer);
					break;
				}
			}
		}
		
		return result;
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

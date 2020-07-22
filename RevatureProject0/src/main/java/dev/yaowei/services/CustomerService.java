package dev.yaowei.services;

import java.util.Set;

import dev.yaowei.entities.Customer;

public interface CustomerService {
	
	// CRUD Like operations 
	Customer establishCustomer(Customer customer);
	Customer getCustomerById(int id);
	Customer updateCustomer(Customer customer);
	boolean deleteCustomerById(int id);
	Set<Customer> getAllCustomers();
	
	//Higher level operations that are not just pure CRUD
	/**
	 * This method increases a school's capcity by a certain amount
	 * 
	 * @param school
	 * @param amount
	 * @return
	 */

	Customer renameCustomer(Customer customer, String newUsername);
	Set<Customer> getCustomersByAccountBalanceLessThan(int num);


}

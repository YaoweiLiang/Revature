package dev.yaowei.daos;
import java.util.Set;

import dev.yaowei.entities.Customer;
public interface CustomerDAO {
	Customer createCustomer(Customer customer); // think of this as a save method
	
	//Read
	Customer getCustomerBycId(int id);
	Set<Customer> getAllCustomer();
	
	//Update
	Customer updateCustomer(Customer school);
	
	
	//Delete
	boolean deleteCustomer(int id);
}

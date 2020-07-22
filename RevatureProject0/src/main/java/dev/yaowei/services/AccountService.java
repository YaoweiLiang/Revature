package dev.yaowei.services;

import java.util.Set;

import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
import dev.yaowei.exceptions.NegativeBalanceExceptions;

public interface AccountService {

	//CRUD
	//Create
	Account createAccount(Account account) throws NegativeBalanceExceptions;
	
	//Read
	Account getAccountById(int id);

	Set<Account> getAccountsByCId(int id);
	Set<Account> getAccountsByCId(Customer customer);
	Set<Account> getAllAccounts();
	
	//update
	Account updateAccount(Account account) throws NegativeBalanceExceptions;
	
	//delete
	boolean deleteAccount(int id);
	boolean deleteAccount(Account account);
	
		

	

}

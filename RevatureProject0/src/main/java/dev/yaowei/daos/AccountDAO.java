package dev.yaowei.daos;

import java.util.Set;

import dev.yaowei.entities.Account;
import dev.yaowei.exceptions.NegativeBalanceExceptions;

public interface AccountDAO {
	//create
	Account createAccounts(Account account) throws NegativeBalanceExceptions;
	//read
	Set<Account> getAllAccounts();
	Account getAccountById(int id); //AId = account ID
	Set<Account> getAccountsByCId(int id); //CId = customer ID
	
	//update
	Account updateAccount(Account account) throws NegativeBalanceExceptions;
	
	//delete
	boolean deleteAccountById(int id);
	
	
}

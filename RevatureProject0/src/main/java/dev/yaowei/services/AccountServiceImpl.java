package dev.yaowei.services;

import java.util.Set;

import dev.yaowei.daos.AccountDAO;
import dev.yaowei.daos.AccountDAOImpl;
import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
import dev.yaowei.exceptions.NegativeBalanceExceptions;

public class AccountServiceImpl implements AccountService {
	private static AccountDAO adao = AccountDAOImpl.getAccountDAOImpl();

	@Override
	public Account createAccount(Account account) throws NegativeBalanceExceptions {
		return adao.createAccounts(account);
	}

	@Override
	public Account getAccountById(int id) {
		return adao.getAccountById(id);
	}

	@Override
	public Set<Account> getAccountsByCId(int id) {
		return this.getAccountsByCId(id);
	}

	@Override
	public Set<Account> getAccountsByCId(Customer customer) {
		return this.getAccountsByCId(customer.getcId());
	}

	@Override
	public Set<Account> getAllAccounts() {
		return adao.getAllAccounts();
	}

	@Override
	public Account updateAccount(Account account) throws NegativeBalanceExceptions {
		return adao.updateAccount(account);
	}

	@Override
	public boolean deleteAccount(int id) {
		return adao.deleteAccountById(id);
	}

	@Override
	public boolean deleteAccount(Account account) {
		return this.deleteAccount(account.getaId());
	}



	
}

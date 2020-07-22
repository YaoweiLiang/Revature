package dev.yaowei.controllers;

import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
import dev.yaowei.services.AccountService;
import dev.yaowei.services.AccountServiceImpl;
import dev.yaowei.services.CustomerService;
import dev.yaowei.services.CustomerServiceImpl;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.Gson;
public class CustomerController {
	public static CustomerService sserv = new CustomerServiceImpl();
	private static AccountService aserv = new AccountServiceImpl(); 
	private static Gson gson = new Gson();
	
	
	public static Handler getAccountWithcIdAndaId = (ctx) ->{
		int cid = Integer.parseInt(ctx.pathParam("cid"));
		int aid = Integer.parseInt(ctx.pathParam("aid"));

		// Customer customer = sserv.getCustomerById(cid);
		 Account account = aserv.getAccountById(aid);
		 if (account.getcId() == cid) {
			 ctx.status(200);
			 ctx.result(gson.toJson(account));
		 }
		 else {
			 ctx.status(404);
		 }		
	};
	

	public static Handler getAllCustomers = (ctx) ->{
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		String username = ctx.queryParam("username");
		Set<Customer> customerSet = sserv.getAllCustomers();
		ArrayList<Customer> customers = toArrayList(customerSet);
		
		if (username != null) {
			for(Customer customer : customers) {
				if (customer.getUsername().equals(username)) {
					result.add(customer);	
				}
			}
		}
		else {
			// no query parameter
			result = customers;
		}
		
		String json = gson.toJson(result.toArray());		
		ctx.status(200); 
		ctx.result(json);
	};
	
	
	// your services should not have to deal with JSONs only your controllers
	public static Handler createCustomer= (ctx) ->{
		//context object ctx is an object that contains the http request and response objects
		// it contains a whole bunch of method for dealing with getting information from the request
		// and sending information in the http response
		
		String customerJson = ctx.body();
		Customer customer = gson.fromJson(customerJson, Customer.class); // transform the json into a Shcool object
		sserv.establishCustomer(customer);
		ctx.status(201); // 201 is the status code to return if you successfully add something
		
		//usually you want to return the created object
		ctx.result(gson.toJson(customer));
	};
	
	
	public static Handler getCustomerById = (ctx)->{
		String id = ctx.pathParam("cid");
		Customer customer = sserv.getCustomerById(Integer.parseInt(id));
		//school object but I want it sent back as a JSON		
		if(customer ==null) {
			ctx.status(404);
		}else {
			String json = gson.toJson(customer);		
			ctx.result(json);	
		}
	
	};
	
	public static Handler updateCustomer = (ctx)->{
		String customerJson = ctx.body();
		Customer customer = gson.fromJson(customerJson, Customer.class);
		sserv.updateCustomer(customer);	
		ctx.result(gson.toJson(customer));
	};
	
	public static Handler deleteCustomer = (ctx) ->{
		String id = ctx.pathParam("cid");
		boolean result = sserv.deleteCustomerById(Integer.parseInt(id));
		
	};
	
	/*
	http://localhost:7010/  <- baseUrl
	http://localhost:7010/customers
	http://localhost:7010/customers/1
	http://localhost:7010/customers/1/accounts
	http://localhost:7010/customers/1/accounts?queryName=queryValue
	baseUrl/customers/:pathParameterValue/entity?queryName=queryValue


	 */
	
	
	public static Handler getAccountsByCustomerId = (ctx) ->{
		ArrayList<Account> result = new ArrayList<Account>();
		
		int customerId = Integer.parseInt(ctx.pathParam("cid"));
		Customer customer = sserv.getCustomerById(customerId);
		Set<Account> accountSet = customer.getCustomerAccounts();
		ArrayList<Account> accounts = toArrayList(accountSet);
		
		String balancegreaterthanString = ctx.queryParam("balancegreaterthan");
		String balancelessthanString = ctx.queryParam("balancelessthan");

		// 0. no filter
		// 1. only balancegreaterthanString has filter
		// 2. only balancelessthanString has filter
		// 3. both balancegreaterthanString and balancelessthanString have filter

		boolean isGreaterThanOnly = balancegreaterthanString != null && balancelessthanString == null;
		if (isGreaterThanOnly) {
			 // /customers/10/accounts?balancegreaterthan=2000
			double balancegreaterthan = Double.parseDouble(balancegreaterthanString);
			for(Account account : accounts) {
				if(account.getBalance() >= balancegreaterthan) {
					result.add(account);
				}
			}
		}
		
		boolean isLessThanOnly = balancegreaterthanString == null && balancelessthanString != null; 
		if (isLessThanOnly) {
		     // /customers/10/accounts?balancelessthan=5000
			double balancelessthan = Double.parseDouble(balancelessthanString);
			for(Account account : accounts) {
				if(account.getBalance() <= balancelessthan) {
					result.add(account);
				}
			}
		}
		
		boolean isBoth = balancegreaterthanString != null && balancelessthanString != null;
		if (isBoth) {
		     // /customers/10/accounts?balancegreaterthan=2000&balancelessthan=5000
			double balancegreaterthan = Double.parseDouble(balancegreaterthanString);
			double balancelessthan = Double.parseDouble(balancelessthanString);
			for(Account account : accounts) {
				if(account.getBalance() >= balancegreaterthan && account.getBalance() <= balancelessthan) {
					result.add(account);
				}
			}
		}
		
		boolean isNone = balancegreaterthanString == null && balancelessthanString == null;
		if (isNone) {
			result = accounts;
		}
		
		String resultJosn = gson.toJson(result.toArray());
		ctx.status(200);
		ctx.result(resultJosn);
	};
	
	private static <T> ArrayList<T> toArrayList(Set<T> set) {
		ArrayList<T> result = new ArrayList<T>();
		Iterator<T> iter = set.iterator();
		while(iter.hasNext()) {
			result.add(iter.next());
		}
		
		return result;
	}
	
}

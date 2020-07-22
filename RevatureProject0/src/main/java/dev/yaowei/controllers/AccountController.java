package dev.yaowei.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.Gson;

import dev.yaowei.services.AccountService;
import dev.yaowei.services.AccountServiceImpl;
import dev.yaowei.services.CustomerService;
import dev.yaowei.services.CustomerServiceImpl;
import io.javalin.http.Handler;
import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
public class AccountController {
	private static AccountService aServ = new AccountServiceImpl();
	private static CustomerService cServ= new CustomerServiceImpl();
	private static Gson gson = new Gson();
	
	// ctx Context object that Javalin gives us to get information from the HTTP request and form an HTTP response
	public static Handler getAllAccountFromCustomerlId = (ctx)->{
		
		int CustomerId = Integer.parseInt(ctx.pathParam("id"));	
		Set<Account> accounts = aServ.getAccountsByCId(CustomerId);
		String json = gson.toJson(accounts);
		
		ctx.result(json);
	};
	
	public static Handler getAccountById = (ctx)->{
		int accountId = Integer.parseInt(ctx.pathParam("aid"));
		Account account = aServ.getAccountById(accountId);
		String json = gson.toJson(account);
		
		ctx.result(json);
	};
	
	public static Handler createAccount = (ctx)->{
		//int accountId = Integer.parseInt(ctx.pathParam("a_id"));	
		
		Account account = gson.fromJson(ctx.body(), Account.class); // convert the JSON body of the request into an object
		//account.setcId(accountId);
		aServ.createAccount(account);
		
		String json = gson.toJson(account);
		ctx.status(201);
		
		ctx.result(json);	
	};
	
	public static Handler updateAccount = (ctx)->{
		Account account = gson.fromJson(ctx.body(), Account.class);
		aServ.updateAccount(account);
		String json = gson.toJson(account);
		ctx.result(json);
	};
	
	
	public static Handler deleteAccount = (ctx) ->{
		int accountId = Integer.parseInt(ctx.pathParam("aid"));
		Boolean result = aServ.deleteAccount(accountId);
		ctx.result(result.toString());
		
	};
	
	public static Handler getAllAccount = (ctx) ->{
		Set<Account> accountSet = aServ.getAllAccounts();
		ArrayList<Account> result = toArrayList(accountSet);

		String json = gson.toJson(result.toArray());		
		ctx.status(200); 
		ctx.result(json);
		
	};
	
	//toArrayList l 70
	private static <T> ArrayList<T> toArrayList(Set<T> set) {
		ArrayList<T> result = new ArrayList<T>();
		Iterator<T> iter = set.iterator();
		while(iter.hasNext()) {
			result.add(iter.next());
		}
		
		return result;
	}

}

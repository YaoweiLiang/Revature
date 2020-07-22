package dev.yaowei.entities;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private int cId;
	private int aId;
	private String username;
	private String password;
	private Set<Account> customerAccounts;
	
	public Customer(int cId, String username, String password) {
		this.cId = cId;
		this.aId = aId;
		this.username=username;
		this.password=password;
		this.customerAccounts = new HashSet<Account>();
	}
	
	public static Customer create(String username, String password) {
		final int autoGenId = 0;
		return new Customer(autoGenId, username, password);
	}
	
	public Customer() {
		super();
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId=cId;
	}
	public int getaId() {
		return aId;
	}
	public void setaId(int aId) {
		this.aId=aId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username=username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public Set<Account> getCustomerAccounts(){
		return customerAccounts;
	}
	public void setCustomerAccounts(Set<Account> customerAccounts) {
		this.customerAccounts = customerAccounts;
	}


}

/*


	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public String toString() {
		return "School [sId=" + sId + ", name=" + name + ", capacity=" + capacity + "]";
	}

}
*/
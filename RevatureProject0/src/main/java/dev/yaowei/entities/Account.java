package dev.yaowei.entities;

public class Account {
	private int aId;
	private int cId;
	private String accountName;
	private int balance;
	
	public Account() {
		super();
	}

	public Account(int aId, int cId, String accountName, int balance) {
		super();
		this.cId=cId;
		this.aId=aId;
		this.accountName=accountName;
		this.balance=balance;
	}
	//added
	public Account(int aId, String accountName, int balance) {
		super();
		this.aId=aId;
		this.accountName=accountName;
		this.balance=balance;
	}

	public static Account create(int cId, String accountName, int balance) {
		final int autoGenId = 0;
		return new Account(autoGenId, cId, accountName, balance);
	}
	//added
	public static Account create( String accountName, int balance) {
		final int autoGenId = 0;
		return new Account(autoGenId, accountName, balance);
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName=accountName;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance=balance;
	}
	@Override
	public String toString() {
		return "Account [cId=" + cId + ", aId=" + aId + ", accountName=" + accountName + ",balance " + balance  + "]";
	}
}
/*	
package dev.yaowei.entities;


//Your entities are objects that are for storing information
//they contain very little to no logic
//they should be Java Beans
public class School {
	
	private int sId; // every entity should have a unique id to identify it
	private String name;	
	private int capacity;
	
	public School() {
		super();
	}
	
	public School(int sId, String name, int capacity) {
		super();
		this.sId = sId;
		this.name = name;
		this.capacity = capacity;
		
	}

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
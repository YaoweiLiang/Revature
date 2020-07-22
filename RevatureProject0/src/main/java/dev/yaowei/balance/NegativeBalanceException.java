package dev.yaowei.balance;

public class NegativeBalanceException extends Exception{
	public NegativeBalanceException() {
		super("Balance cannot be less than 0");
	}
}

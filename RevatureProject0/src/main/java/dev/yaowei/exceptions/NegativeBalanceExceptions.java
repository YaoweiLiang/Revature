package dev.yaowei.exceptions;



public class NegativeBalanceExceptions extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8187222779955196671L;

	public NegativeBalanceExceptions(){
		super("Balance cannot be less than 0");
	}
}

package com.exception;

public class BankException extends Exception {
	/**
	 * Bank Exception
	 */
	private static final long serialVersionUID = 1L;

	public BankException (String message) {
		super(message);
	}
}

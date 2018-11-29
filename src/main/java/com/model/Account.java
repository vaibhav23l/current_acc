package com.model;

import java.sql.Timestamp;

public class Account {
	public enum AccountType {
		CURRENT, LOAN, SAVING;
	}

	private int accountId;
	private int customerId;
	private AccountType accountType;
	private double balance;
	private Timestamp createDate;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", customerId=" + customerId
				+ ", accountType=" + accountType + ", balance=" + balance
				+ ", createDate=" + createDate + "]";
	}

}

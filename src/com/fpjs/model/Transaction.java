package com.fpjs.model;

import java.math.BigDecimal;

public class Transaction {

	private String ID;
	private BigDecimal Amount;
	private String BankCountryCode;

	public Transaction(String iD, BigDecimal amount, String bankCountryCode) {
		super();
		ID = iD;
		Amount = amount;
		BankCountryCode = bankCountryCode;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public BigDecimal getAmount() {
		return Amount;
	}

	public void setAmount(BigDecimal amount) {
		Amount = amount;
	}

	public String getBankCountryCode() {
		return BankCountryCode;
	}

	public void setBankCountryCode(String bankCountryCode) {
		BankCountryCode = bankCountryCode;
	}

	@Override
	public String toString() {
		return "Transaction [ID=" + ID + ", Amount=" + Amount + ", BankCountryCode=" + BankCountryCode + "]";
	}

}

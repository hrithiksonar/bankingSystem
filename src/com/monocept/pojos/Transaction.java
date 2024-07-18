package com.monocept.pojos;

public class Transaction {

	private int accountNo;
	private String fullName;
	private String transaction_type;
	private int amount;
	private String date;
	private String id;
	private String time;

	public Transaction(int accountNo, String fullName, String transaction_type, int amount, String date, String id,
			String time) {
		super();
		this.accountNo = accountNo;
		this.fullName = fullName;
		this.transaction_type = transaction_type;
		this.amount = amount;
		this.date = date;
		this.id = id;
		this.time = time;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public String getFullName() {
		return fullName;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public int getAmount() {
		return amount;
	}

	public String getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

}

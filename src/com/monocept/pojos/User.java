package com.monocept.pojos;

public class User {
	private String username, password, fullname, email, mob, status;
	private float balance;
	private int accountNo;

	public User(int accountNo, String username, String password, String fullname, String email, String mob,
			String status) {
		super();
		this.balance = 0;
		this.accountNo = accountNo;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.mob = mob;
		this.status = status;
	}

	public User(int acc, String username, String password, String fullname, String email, String mob, String status,
			Float ammount) {
		super();
		this.balance = ammount;
		this.accountNo = acc;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.mob = mob;
		this.status = status;
	}

	public User(String username, String password, String fullname, String email, String mob, String status) {
		super();
		this.balance = 0;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.mob = mob;
		this.status = status;
	}

	public User(int accountNo, String username, String fullname, String email, String mob) {
		super();
		this.accountNo = accountNo;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.mob = mob;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public float getBalance() {
		return balance;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFullname() {
		return fullname;
	}

	public String getEmail() {
		return email;
	}

	public String getMob() {
		return mob;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}

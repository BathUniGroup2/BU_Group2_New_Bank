package com.server;

import java.util.ArrayList;

public class Customer {

	private final ArrayList<Account> accounts;
	private String username;
	private String password;
	
	public Customer(final String username, final String password) {
		this.username = username;
		this.password = password;
		accounts = new ArrayList<>();
	}

	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			if (s.equals("")) {
				s += a.toString();
				continue;
			}
			s += ", " + a.toString();
		}
		return s;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public ArrayList<Account> getAccounts() { return accounts; }

	public void addAccount(Account account) {
		accounts.add(account);
	}
}

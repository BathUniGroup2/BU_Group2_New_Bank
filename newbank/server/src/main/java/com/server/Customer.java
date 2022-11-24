package com.server;

import java.util.ArrayList;

public class Customer {

	private final ArrayList<Account> accounts;

	public Customer() {
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

	public ArrayList<Account> getAccounts() { return accounts; }

	public void addAccount(Account account) {
		accounts.add(account);
	}
}
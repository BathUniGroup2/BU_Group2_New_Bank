package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private final ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		StringBuilder s = new StringBuilder();
		for(Account a : accounts) {
			s.append(a.toString());
		}
		return s.toString();
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}

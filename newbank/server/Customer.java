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
			if (s.toString().equals("")) {
				s.append(a.toString());
				continue;
			}
			s.append(", ").append(a.toString());
		}
		return s.toString();
	}

	public ArrayList<Account> getAccounts() { return accounts; }

	public void addAccount(Account account) {
		accounts.add(account);
	}
}

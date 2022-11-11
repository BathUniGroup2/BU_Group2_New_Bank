package newbank.server;

import java.util.HashMap;
import java.util.ArrayList;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account(Account.AccountType.MAIN, 1000.0));
		customers.put("Bhagy", bhagy);

		Customer christina = new Customer();
		christina.addAccount(new Account(Account.AccountType.SAVINGS, 1500.0));
		customers.put("Christina", christina);

		Customer john = new Customer();
		john.addAccount(new Account(Account.AccountType.CHECKING, 250.0));
		customers.put("John", john);
	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request, String arg) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				case "NEWACCOUNT" : return newAccount(customer, arg);
				default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String newAccount(CustomerID customer, String accountType) {
		// Fail if no account type argument was given
		if (accountType.length() < 1) return "FAIL";

		Customer currentCustomer = customers.get(customer.getKey());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();

		// Fail if customer has existing account of this type
		for (int i = 0; i < currentAccounts.size(); i++) {
			Account.AccountType currentType = currentAccounts.get(i).getAccountType();
			if (currentType.toString().equals(accountType)) return "FAIL";
		}

		// Create new account and add to customer account list
		try {
			Account newAccount = new Account(Account.stringToAccountType(accountType), 0.0);
			currentCustomer.addAccount(newAccount);
			return "SUCCESS";
		} catch(Exception e) {
			return "FAIL";
		}
	}

}

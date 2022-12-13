package com.server;

import java.util.ArrayList;
import java.util.HashMap;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private final HashMap<String, Customer> customers;
	private static HashMap<String, Password> passwords;

	public NewBank() {
		customers = new HashMap<>();
		passwords = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer("Bhagy", "Bhagy123");
		bhagy.addAccount(new Account(Account.AccountType.MAIN, 1000.0));
		customers.put("Bhagy", bhagy);
		passwords.put("Bhagy", new Password("Bhagy1234"));

		Customer christina = new Customer("Christina", "Christina123");
		christina.addAccount(new Account(Account.AccountType.SAVINGS, 1500.0));
		customers.put("Christina", christina);

		Customer john = new Customer("John", "test");
		john.addAccount(new Account(Account.AccountType.CHECKING, 250.0));
		customers.put("John", john);
	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String username, String password) {
		if (customers.containsKey(username)) {
			if (customers.get(username).getPassword().equals(password)) {
				return new CustomerID(username);
			}
		}
		  return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request, String[] args) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				case "NEWACCOUNT" : return newAccount(customer, args);
				case "MOVE" : return move(customer, args);
				case "PAY" : return pay(customer, args);
				default : return "FAIL";
			}
		}
		return "FAIL";
	}
	/**
	 * This method is used to show the customer's accounts
	 *
	 * @param customer CustomerID of the customer
	 * @return the names of the customer's accounts and their balances
	 */
	private String showMyAccounts(CustomerID customer) {
		Customer currentCustomer = customers.get(customer.getKey());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();
		if (currentAccounts.size() < 1) return null;
		ArrayList<String> accountList = new ArrayList<>();
		for (Account currentAccount : currentAccounts) {
			{
				accountList.add(currentAccount.getAccountType().toString() + ": " + currentAccount.getBalance());
			}
		}
		return String.join("\n", accountList);
	}

	private String newAccount(CustomerID customer, String[] args) {
		// User must input enough arguments
		if (args == null || args.length < 1) return "FAIL";

		String accountType = args[0];
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
		} catch (Exception e) {
			return "FAIL";
		}
	}

	/**
	 * Transfer money from one account to another
	 * @param customer CustomerID of the customer making the payment
	 * @param args The arguments for the transfer (customerAccountType, payee, payeeAccountType, amount)
	 * @return String of "SUCCESS" or "FAIL"
	 */
	private String pay(CustomerID customer, String[] args) {
		// User must input enough arguments
		if (args == null || args.length < 4) return "FAIL";

		String customerAccountType = args[0];
		String payee = args[1];
		String payeeAccountType = args[2];
		String amount = args[3];

		// Fail if no payee, account or amount argument was given
		if (payee.length() < 1 || customerAccountType.length() < 1 ||
				amount.length() < 1 || payeeAccountType.length() < 1) return "FAIL";

		Customer currentCustomer = customers.get(customer.getKey());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();

		Account currentAccount = null;
		// Fail if customer has no accounts
		if (currentAccounts.size() < 1) return "FAIL";

		// Fail if customer has no account of corresponding to customerAccountType
		for (Account _currentAccount : currentAccounts) {
			if (_currentAccount.getAccountType().toString().equals(customerAccountType)) {
				try {
					// Fail if customer has no sufficient funds in the selected account
					if (_currentAccount.getBalance() < Double.parseDouble(amount)) {
						return "FAIL";

					} else {
						currentAccount = _currentAccount;
					}
				} catch (NumberFormatException e) {
					// Incorrect arg added for arg[2] - must be numeric string
					return "FAIL";
				}
			}
		}
		if (currentAccount == null) return "FAIL";


		Account payeeAccount = null;
		// If the payee is a customer of the bank, transfer the money to their account
		if (customers.containsKey(payee)) {
			Customer payeeCustomer = customers.get(payee);
			ArrayList<Account> payeeAccounts = payeeCustomer.getAccounts();
			// Fail if payee has no accounts
			if (payeeAccounts.size() < 1) return "FAIL";
			// Fail if payee has no account of corresponding to payeeAccountType
			for (Account _payeeAccount : payeeAccounts) {
				if (_payeeAccount.getAccountType().toString().equals(payeeAccountType)) {
					payeeAccount = _payeeAccount;
					currentAccount.updateBalance('-', Double.parseDouble(amount));
					payeeAccount.updateBalance('+',  Double.parseDouble(amount));
				}
			}
			if (payeeAccount == null) return "FAIL";
			// no functionality to transfer money outside the bank requested
		} else return "FAIL";

		// succeed if no condition above fails
		return "SUCCESS";
	}


	private String move(CustomerID customer, String[] args) {
		// User must input enough arguments
		if (args == null || args.length < 3) return "FAIL";

		String from = args[0];
		String to = args[1];
		String amount = args[2];

		Customer currentCustomer = customers.get(customer.getKey());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();
		double amountDouble;

		try {
			amountDouble = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// Incorrect arg added for arg[2] - must be numeric string
			return "FAIL";
		}

		// Fail if user only has one account type
		if (currentAccounts.size() < 2) return "FAIL";
		
		// Check if to and from accounts exist if not fail
		// Once exist check if sufficient balance for transfer
		int fromIndex = 0;
		int toIndex = 0;
		Account fromMatch = null;
		Account toMatch = null;

		for (int i = 0; i < currentAccounts.size(); i++) {
			Account checkAccount = currentAccounts.get(i);
			Account.AccountType checkAccountType = checkAccount.getAccountType();
			if (checkAccountType.toString().equals(from)){
				if (checkAccount.getBalance() < amountDouble) {
					return "FAIL";
				} else {
					fromMatch = new Account(checkAccountType, checkAccount.updateBalance('-', amountDouble));
					fromIndex = i;
				}
			} else if (checkAccountType.toString().equals(to)){
				toMatch = new Account(checkAccountType, checkAccount.updateBalance('+', amountDouble));
				toIndex = i;
			} else return "FAIL";
		}

		if (fromMatch == null || toMatch == null) return "FAIL";

		// Move amount from original account to new account
		try {
			currentAccounts.set(fromIndex, fromMatch);
			currentAccounts.set(toIndex, toMatch);
			return "SUCCESS";
		} catch(Exception e) {
			return "FAIL";
		}

	}

	public HashMap<String,Customer> getCustomers() {
		return customers;
	}
}

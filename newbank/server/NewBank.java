package newbank.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private final HashMap<String,Customer> customers;

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account(Account.AccountType.MAIN, 1000.00));
		customers.put("Bhagy", bhagy);

		Customer christina = new Customer();
		christina.addAccount(new Account(Account.AccountType.SAVINGS, 1500.00));
		customers.put("Christina", christina);

		Customer john = new Customer();
		john.addAccount(new Account(Account.AccountType.CHECKING, 250.00));
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
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.key())) {

			//TODO: type, payee and amount should come from user input in UI
			//the temporary variables below are for testing only:
			String acc_type = "MAIN";
			String payee = "John";
			String amount = "100.00";

			return switch (request) {
				case "SHOWMYACCOUNTS" -> showMyAccounts(customer);
				case "NEWACCOUNT" -> newAccount(customer, acc_type);
				case "PAY" -> pay(customer,payee, amount);
				default -> "FAIL";
			};
		}
		return "FAIL";
	}

	/**
	 * This method is used to show the customer's accounts
	 * @param customer CustomerID of the customer
	 * @return the names of the customer's accounts and their balances
	 */
	private String showMyAccounts(CustomerID customer) {
		Customer currentCustomer = customers.get(customer.key());
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


	private String newAccount(CustomerID customer, String accountType) {
		// Fail if no account type argument was given
		if (accountType.length() < 1) return "FAIL";

		Customer currentCustomer = customers.get(customer.key());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();

		// Fail if customer has existing account of this type
		for (Account currentAccount : currentAccounts) {
			Account.AccountType currentType = currentAccount.getAccountType();
			if (currentType.toString().equals(accountType)) return "FAIL";
		}

		// Create new account and add to customer account list
		try {
			Account newAccount = new Account(Account.stringToAccountType(accountType), 0.00);
			currentCustomer.addAccount(newAccount);
			return "SUCCESS";
		} catch(Exception e) {
			return "FAIL";
		}
	}

	/**
	 * Transfer money from one account to another
	 * @param customer CustomerID of the customer making the payment
	 * @param payee String of the payee's name
	 * @param amount String of the amount to be paid
	 * @return String of "SUCCESS" or "FAIL"
	 */
	private String pay(CustomerID customer, String payee, String amount) {
		// Fail if no payee or amount argument was given
		if (payee.length() < 1 || amount.length() < 1) return "FAIL";

		Customer currentCustomer = customers.get(customer.key());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();

		// Fail if customer has no accounts
		if (currentAccounts.size() < 1) return "FAIL";

		// Fail if customer has no account with sufficient funds
		for (Account currentAccount : currentAccounts) {
			if (currentAccount.getBalance() >= Double.parseDouble(amount)) {
				currentAccount.setBalance(currentAccount.getBalance() - Double.parseDouble(amount));
			} else return "FAIL";
		}
	// succeed if no condition above fails
	 return "SUCCESS";
	}
}


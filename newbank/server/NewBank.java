package newbank.server;

import java.util.HashMap;
import java.util.ArrayList;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private final HashMap<String, Customer> customers;

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
		if (customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request, String[] args) {
		if (customers.containsKey(customer.key())) {
			return switch (request) {
				case "SHOWMYACCOUNTS" -> showMyAccounts(customer);
				case "NEWACCOUNT" -> newAccount(customer, args);
				case "PAY" -> pay(customer, args);
				default -> "FAIL";
			};
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

	private String newAccount(CustomerID customer, String[] accountTypes) {
		String accountType = accountTypes[0];
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
	private String pay(CustomerID customer, String[] args)
	{
		String customerAccountType = args[0];
		String payee = args[1];
		String payeeAccountType = args[2];
		String amount = args[3];

		// Fail if no payee, account or amount argument was given
		if (payee.length() < 1 || customerAccountType.length() < 1 ||
				amount.length() < 1 || payeeAccountType.length() < 1) return "FAIL";

		Customer currentCustomer = customers.get(customer.key());
		ArrayList<Account> currentAccounts = currentCustomer.getAccounts();

		Account currentAccount = null;
		// Fail if customer has no accounts
		if (currentAccounts.size() < 1) return "FAIL";
		// Fail if customer has no account of corresponding to customerAccountType
		for (Account _currentAccount : currentAccounts) {
			if (_currentAccount.getAccountType().toString().equals(customerAccountType)) {
				// Fail if customer has no sufficient funds in the selected account
				if (_currentAccount.getBalance() < Double.parseDouble(amount)) {
					return "FAIL";
				} else {
					currentAccount = _currentAccount;
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
					currentAccount.setBalance(currentAccount.getBalance() - Double.parseDouble(amount));
					payeeAccount.setBalance(payeeAccount.getBalance() + Double.parseDouble(amount));
				}
			}
			if (payeeAccount == null) return "FAIL";
			// no functionality to transfer money outside the bank requested
		} else return "FAIL";

		// succeed if no condition above fails
		return "SUCCESS";
	}
}
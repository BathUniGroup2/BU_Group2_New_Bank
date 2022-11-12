package newbank.server;
import java.util.HashMap;

public class NewBank {

	//Declaring ui variables as constants in case the client wants to change the ui looks
	final static String SHOWMYACCOUNTS = "SHOWMYACCOUNTS";
	final static String NEWACCOUNT = "NEWACCOUNT";
	final static String SUCCESS = "SUCCESS";
	final static String FAIL = "FAIL";

	private static final NewBank bank = new NewBank();
	private final HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.00));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.00));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.00));
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

		if(customers.containsKey(customer.getKey())) {
			if (SHOWMYACCOUNTS.equals(request)) {
				return showMyAccounts(customer);
			}
			if (request.startsWith(NEWACCOUNT)) {
				String account_name = request.replace(NEWACCOUNT, "");
				return newAccount(account_name, customer);
			}
			else {
				return FAIL;
			}
		}
		return FAIL;
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}
	private String newAccount(String account_name, CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString().contains(account_name) ? FAIL : SUCCESS;
	}


}


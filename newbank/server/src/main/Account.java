package newbank.server.src.main;

public class Account {


	public enum AccountType {
		MAIN("Main"),
		SAVINGS("Savings"),
		CHECKING("Checking");

		private final String type;

		AccountType(String type) {
			this.type = type;
		}

		public String toString() {
			return type;
		}
	}
	private final AccountType type;
	private double balance;

	public Account(AccountType type, double balance) {
		this.type = type;
		this.balance = balance;
	}

	public double getBalance() { return balance; }
	
	public String toString() {
		return (type.toString() + ": " + balance);
	}

	public AccountType getAccountType() { return type; }

	public static AccountType stringToAccountType(String type) throws Exception {
		return switch (type) {
			case "Main" -> AccountType.MAIN;
			case "Savings" -> AccountType.SAVINGS;
			case "Checking" -> AccountType.CHECKING;
			default -> throw new Exception();
		};
	}

	public double updateBalance(char operand, double amountDouble) {
		if (operand == '+'){
			balance += amountDouble;
		} else if (operand == '-'){
		 	balance -= amountDouble;
		}
		return balance;
	}
}

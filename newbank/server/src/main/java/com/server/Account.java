package com.server;

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
	private final double openingBalance;

	public Account(AccountType type, double openingBalance) {
		this.type = type;
		this.openingBalance = openingBalance;
	}

	public double getBalance() { return openingBalance; }
	
	public String toString() {
		return (type.toString() + ": " + openingBalance);
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
		double newBalance = 0;
		if (operand == '+'){
			newBalance = openingBalance + amountDouble;
		} else if (operand == '-'){
		 	newBalance = openingBalance - amountDouble;
		}
		return newBalance;
	}
}

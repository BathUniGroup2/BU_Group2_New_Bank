package com.server;

public class Microloan {

    private final String user;

    private final String account;

    private final double amount;

    private final double interest;

    private final int months;

    public Microloan (String user, String account, double amount, double interest, int months) {
        this.user = user;
        this.account = account;
        this.amount = amount;
        this.interest = interest;
        this.months = months;
    }

    public String getUser() { return user; }

    public String getAccount() { return account; }

    public double getAmount() { return amount; }

    public double getInterest() { return interest; }

    public int getMonths() { return months; }
}

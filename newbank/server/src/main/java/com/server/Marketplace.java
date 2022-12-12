package com.server;

import java.util.ArrayList;
public class Marketplace {
    private static final Marketplace marketplace = new Marketplace();

    private ArrayList<Microloan> loans = new ArrayList<Microloan>();

    public static Marketplace getMarketplace() {
        return marketplace;
    }

    public ArrayList<Microloan> getLoans () {
        return loans;
    }
    public void addLoan (String user, double amount, String account, double interest, int months)  {
        Microloan newLoan = new Microloan(user, account, amount, interest, months);
        loans.add(newLoan);
    }
}

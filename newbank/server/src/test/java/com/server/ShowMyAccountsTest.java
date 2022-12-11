package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShowMyAccountsTest {
    @Test
    public void correctFormatTest() {
        NewBank bank = NewBank.getBank();
        CustomerID customerId = new CustomerID("Bhagy");
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 1000.0", accountsString);
        bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Savings"});
        bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Checking"});
        accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 1000.0\nSavings: 0.0\nChecking: 0.0", accountsString);
    }
}

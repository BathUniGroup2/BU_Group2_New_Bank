package com.server;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class PayTest {
    NewBank bank;
    private CustomerID customerId = new CustomerID("Bhagy");

    @Before
    public void setup() {
        bank = new NewBank();
    }

    @Test
    public void successfulPayTest() {
        String output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Savings", "100"});
        assertEquals("SUCCESS", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 900.0", accountsString);
        Account payeeAccount = bank.getCustomers().get("Christina").getAccounts().get(0);
        assertEquals(1600.0, payeeAccount.getBalance(), 0.0);
    }

    @Test
    public void notEnoughCreditTest() {
        String output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Savings", "1100"});
        assertEquals("FAIL", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 1000.0", accountsString);
        Account payeeAccount = bank.getCustomers().get("Christina").getAccounts().get(0);
        assertEquals(1500.0, payeeAccount.getBalance(), 0.0);
    }

    @Test
    public void incorrectPayeeAccountTest() {
        String output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Main", "100"});
        assertEquals("FAIL", output);
    }

    @Test
    public void decimalInputTest() {
        String output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Savings", ".55"});
        assertEquals("SUCCESS", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 999.45", accountsString);
        Account payeeAccount = bank.getCustomers().get("Christina").getAccounts().get(0);
        assertEquals(1500.55, payeeAccount.getBalance(), 0.0);
    }

    @Test
    public void invalidPayeeTest() {
        String output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Bob", "Main", "100"});
        assertEquals("FAIL", output);
    }

    @Test
    public void incorrectArgsTest() {
        String output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Main"});
        assertEquals("FAIL", output);
        output = bank.processRequest(customerId, "PAY", new String[]{"Christina", "Main", "100"});
        assertEquals("FAIL", output);
        output = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "100", "Main"});
        assertEquals("FAIL", output);
        output = bank.processRequest(customerId, "PAY", new String[]{});
        assertEquals("FAIL", output);
    }
}

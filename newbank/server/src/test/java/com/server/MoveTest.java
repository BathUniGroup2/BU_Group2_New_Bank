package com.server;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class MoveTest {
    NewBank bank;
    private CustomerID customerId = new CustomerID("Bhagy");

    @Before
    public void setup() {
        bank = new NewBank();
        bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Savings"});
    }

    @Test
    public void successfulMoveTest() {
        String output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "Savings", "100"});
        assertEquals("SUCCESS", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 900.0\nSavings: 100.0", accountsString);
    }

    @Test
    public void decimalInputTest() {
        String output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "Savings", "100.23"});
        assertEquals("SUCCESS", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 899.77\nSavings: 100.23", accountsString);
        output = bank.processRequest(customerId, "MOVE", new String[]{"Savings", "Main", ".23"});
        assertEquals("SUCCESS", output);
        accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 900.0\nSavings: 100.0", accountsString);
    }

    @Test
    public void zeroInputTest() {
        String output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "Savings", "0"});
        assertEquals("SUCCESS", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 1000.0\nSavings: 0.0", accountsString);
    }

    @Test
    public void sameAccountTest() {
        String output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "Main", "0"});
        assertEquals("FAIL", output);
    }

    @Test
    public void notEnoughCreditTest() {
        String output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "Savings", "1100"});
        assertEquals("FAIL", output);
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 1000.0\nSavings: 0.0", accountsString);
    }

    @Test
    public void incorrectArgsTest() {
        String output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "Savings"});
        assertEquals("FAIL", output);
        output = bank.processRequest(customerId, "MOVE", new String[]{"Main", "100", "Savings"});
        assertEquals("FAIL", output);
        output = bank.processRequest(customerId, "MOVE", new String[]{"100", "Savings"});
        assertEquals("FAIL", output);
        output = bank.processRequest(customerId, "MOVE", new String[]{});
        assertEquals("FAIL", output);
    }
}

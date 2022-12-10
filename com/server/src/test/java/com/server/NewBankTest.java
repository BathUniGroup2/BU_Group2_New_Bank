package com.server.src.test.java.com.server;

import com.server.src.main.java.com.server.CustomerID;
import com.server.src.main.java.com.server.NewBank;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewBankTest {
    @Test
    public void TestShowMyAccounts() {
        NewBank bank = NewBank.getBank();
        CustomerID customerId = new CustomerID("Bhagy");
        String accountsString = bank.processRequest(customerId, "SHOWMYACCOUNTS", new String[]{});
        assertEquals("Main: 1000.0", accountsString);
    }
}
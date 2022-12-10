package com.server.src.test.java.com.server;
import com.server.src.main.java.com.server.CustomerID;
import com.server.src.main.java.com.server.NewBank;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PayTest {

    NewBank bank = NewBank.getBank();
    CustomerID customerId = new CustomerID("John");


    // Test transfer with sufficient funds
    @Test
    public void TestPay_sufficient() {
        String payString = bank.processRequest(customerId, "PAY", new String[]{"Main", "Bhagy", "Main", "100"});
        assertEquals("SUCCESS", payString);
    }

    // Test transfer with insufficient funds
    @Test
    public void TestPay_insufficient() {
        String payString = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Main", "1000"});
        assertEquals("FAIL", payString);
    }

    // Test transfer to non-existent account
    @Test
    public void TestPay_invalidAccount() {
        String payString = bank.processRequest(customerId, "PAY", new String[]{"Main", "Christina", "Savings", "100"});
        assertEquals("FAIL", payString);
    }


}
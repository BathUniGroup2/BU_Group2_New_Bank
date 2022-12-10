package com.server.src.test.java.com.server;

import com.server.src.main.java.com.server.CustomerID;
import com.server.src.main.java.com.server.NewBank;
import  org.junit.Test;

import static org.junit.Assert.*;

public class NewAccountTest{

    NewBank bank = NewBank.getBank();
    CustomerID customerId = new CustomerID("Bhagy");
    
    // Account already exists test
    @Test
    public void accountAlreadyExistTest(){
        String newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Main"});
        assertEquals("FAIL", newAccountString);
    }
    
    // Account name to be added consists of typo
    @Test
    public void incorrectAccountTest(){
        String newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{"SAVINGS"});
        assertEquals("FAIL", newAccountString);
    }
    
    // No input argument given
    @Test
    public void nullAccountTest(){
        String newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{});
        assertEquals("FAIL", newAccountString);
    }
    
    // New account that doesn't exist created
    @Test
    public void newAccountTest(){
        String newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Savings"});
        assertEquals("SUCCESS", newAccountString);
        newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Checking"});
        assertEquals("SUCCESS", newAccountString);
    }

}

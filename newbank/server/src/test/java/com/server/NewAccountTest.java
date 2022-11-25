package com.server;

import org.junit.Test;

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
    
    // No argument provided
    @Test (expected = IndexOutOfBoundsException.class)
    public void nullAccountTest(){
        bank.processRequest(customerId, "NEWACCOUNT", new String[]{});
    }
    
    // New account that doesn't exist created
    @Test
    public void newAccountTest(){
        String newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Savings"});
        assertEquals("SUCCESS", newAccountString);
    }

    // New account that doesn't exist created
    @Test
    public void newAccountTest2(){
        String newAccountString = bank.processRequest(customerId, "NEWACCOUNT", new String[]{"Checking"});
        assertEquals("SUCCESS", newAccountString);
    }
}

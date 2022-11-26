package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Ignore;

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
    
    // No argument provided - this currently isn't covered by the NewAccount function.
    // A ticket is in the backlog to cover IndexOutOfBoundsException and update this test to return SUCCESS or FAIL
    @Ignore
    @Test (expected = IndexOutOfBoundsException.class)
    public void nullAccountTest(){
        bank.processRequest(customerId, "NEWACCOUNT", new String[]{});
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

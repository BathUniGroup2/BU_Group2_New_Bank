package com.server.src.main.java.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class NewBankClientHandler extends Thread{

	private final NewBank bank;
	private final BufferedReader in;
	private final PrintWriter out;
	private final CLI cLI;


	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
		cLI = new CLI(s);
	}

	public void run() {
		// keep getting requests from the client and processing them
		cLI.displayWelcomeScreen();
		try {
			// ask for user name
			cLI.displayEnterUsername();
			String userName = in.readLine();
			// ask for password
			cLI.displayEnterPassword();
			String password = in.readLine();
			cLI.displayCheckingStatus();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// handles any possible exception during the call to "sleep()"
				e.printStackTrace(); // prints the exception stack trace to the console
				System.exit(0);
			}
			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them
			if(customer != null) {
				cLI.displaySuccessMsg();
				cLI.displayNavigation();
				cLI.displayOptions();
				while(true) {
					String input = in.readLine();
					if (input.length() < 1) {
						cLI.displayFailMsg();
						cLI.displayNavigation();
					} else{
						// To allow for a CMD arg we split by space
						String[] inputArray = input.split(" ");
						String request = inputArray[0];
						String[] args = {};
						if (inputArray.length == 2 || inputArray.length == 4) {
							args = Arrays.copyOfRange(inputArray, 1, inputArray.length);
						} 

						System.out.println("Request from " + customer.getKey());
            // a break condition to exit the banking loop
					  if (request.equals("QUIT")) {
						cLI.displayQuit();
						  break; 
              }
						String response = bank.processRequest(customer, request, args);
						out.println(response);
						cLI.displayNavigation();
					}
				}
			}
			else {
				cLI.displayFailMsg();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}

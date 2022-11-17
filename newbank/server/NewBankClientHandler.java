package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{

	private final NewBank bank;
	private final BufferedReader in;
	private final PrintWriter out;


	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}

	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them
			if(customer != null) {
				out.println("Log In Successful. What do you want to do?");
				while(true) {
					String input = in.readLine();
					if (input.length() < 1) {
						out.println("Command required, try again. What do you want to do?");
					} else{
						// To allow for a CMD arg we split by space
						String[] inputArray = input.split(" ");
						String request = inputArray[0];
						String arg = "";
						String arg2 = "";
						String arg3 = "";

						if (inputArray.length == 2) {
							arg = inputArray[1];
						} else if (inputArray.length == 4){
							arg = inputArray[1];
							arg2 = inputArray[2];
							arg3 = inputArray[3];
						} else {
							System.out.println("FAIL");
						}

						System.out.println("Request from " + customer.getKey());
            // a break condition to exit the banking loop
					  if (arg.equals("QUIT")) {
						  break; 
              }
						String response = bank.processRequest(customer, request, arg, arg2, arg3);
						out.println(response);
					}
				}
			}
			else {
				out.println("Log In Failed");
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

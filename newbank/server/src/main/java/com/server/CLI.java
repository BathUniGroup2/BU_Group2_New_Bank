package com.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import de.vandermeer.asciitable.AsciiTable;

public class CLI {

    private static PrintWriter out;
    AsciiTable at = new AsciiTable();
    
    public CLI(Socket s) throws IOException {
        out = new PrintWriter(s.getOutputStream(), true);
    }

     public void displayWelcomeScreen(){
        out.flush();
        out.println("███▄▄▄▄      ▄████████  ▄█     █▄      ▀█████████▄    ▄████████ ███▄▄▄▄      ▄█   ▄█▄ ");
        out.println("███▀▀▀██▄   ███    ███ ███     ███       ███    ███  ███    ███ ███▀▀▀██▄   ███ ▄███▀ ");
        out.println("███   ███   ███    █▀  ███     ███       ███    ███  ███    ███ ███   ███   ███▐██▀   ");
        out.println("███   ███  ▄███▄▄▄     ███     ███      ▄███▄▄▄██▀   ███    ███ ███   ███  ▄█████▀    ");
        out.println("███   ███ ▀▀███▀▀▀     ███     ███     ▀▀███▀▀▀██▄ ▀███████████ ███   ███ ▀▀█████▄    ");
        out.println("███   ███   ███    █▄  ███     ███       ███    ██▄  ███    ███ ███   ███   ███▐██▄   ");
        out.println("███   ███   ███    ███ ███ ▄█▄ ███       ███    ███  ███    ███ ███   ███   ███ ▀███▄ ");
        out.println(" ▀█   █▀    ██████████  ▀███▀███▀      ▄█████████▀   ███    █▀   ▀█   █▀    ███   ▀█▀ ");
        out.println();
    }

    public void displayOptions(){
        at.addRule();
        at.addRow("Command", "Arguments", "Functionality");
        at.addRule();
        at.addRow("SHOWMYACCOUNTS", "n/a", "Outputs all accounts owned by user stored in the system and their respective balance");
        at.addRule();
        at.addRow("NEWACCOUNT", "accountType (Main, Savings, Checking)", "Creates a new account for the user in the system, outputs SUCCESS or FAIL (user may only have one type of each account)");
        at.addRule();
        at.addRow("MOVE", "MOVE (Main, Savings, 100)", "Takes in three arguments, the first is the account money is transferred out of. The second is the account money is transferred in. The last is the amount you'd like to transfer");
        at.addRule();
        at.addRow("MARKETPLACE", "n/a", "Outputs all current microloans available in the system");
        at.addRule();
        at.addRow("MICROLOAN", "MICROLOAN(100, `Main`, 10.5, 3)", "Creates a new microloan and saves in the system, takes four arguments - amount, account, interest, term in months");
        at.addRule();
        at.addRow("QUIT", "n/a", "Quits the program");
        at.addRule();
        String rend = at.render();
        out.println(rend);
        out.println("Please choose one of the listed options:");
    }

    public void displayCheckingStatus(){
        out.println("Checking the details provided...\n");
    }

    public void displaySuccessMsg(){
        out.println("Success!");
    }

    public void displayFailMsg(){
        out.println("This action has Failed.\n");
    }

    public void displayQuit(){
        out.println("Are you sure you want to quit? (y/n)");
    }

    public void displayEnterUsername(){
        out.println("Please provide your username");
    }

    public void displayEnterPassword(){
        out.println("Please provide your password");
    }

    public void displayNavigation(){
        out.println("What would you like to do next?");
    }
}

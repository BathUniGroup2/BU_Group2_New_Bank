# BU_Group2_New_Bank

Consists of server and command line client interface

With both server and client running locally, user can input commands to interact with the service

Some commands require command line arguments to function in the format `COMMAND argument`

Commands are case sensitive (must be uppercase)

## Available functionality

| Command        | Arguments                                   | Functionality                                                                                                               |
| -------------- | ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| SHOWMYACCOUNTS | n/a                                         | Outputs all accounts owned by user stored in the system and their respective balance                                        |
| NEWACCOUNT     | accountType (`Main`, `Savings`, `Checking`) | Creates a new account for the user in the system, outputs `SUCCESS` or `FAIL` (user may only have one type of each account) |
| PAY |  CustomerID customer, String customerAccountType, String payee, String payeeAccountType,String amount | Transfers money to the specified account in the same bank if the account is found and the customer has enough money and returns `SUCCESS`. Otherwise returns `FAIL`  
| QUIT | n/a                                         | Quits the program                                        |

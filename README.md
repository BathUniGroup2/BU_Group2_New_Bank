# BU_Group2_New_Bank

Consists of server and command line client interface

## Build and run

The server project uses the Maven build tool, the client project is currently a simple Java build

### VSCode

- Maven must be installed to build and run server
  - Directly available https://maven.apache.org/
  - Via HomeBrew `brew install maven`
  - Termninal command for Linux 'sudo pacman -S maven' for Arch based distros or 'apt-get install maven' for Ubuntu/Debian
- Install VSCode extension, Extension Pack for Java
- After opening Server project, on sidebar you should open Maven dropdown panel
  - Click play button by `clean` command
  - Click play button by `install` command
- Open Java projects tab
  - Click `rebuild all` icon (tools icon)
- On top of editor click 'Run Java' icon (play icon)

### IntelliJ

- Run build to generate `target` folder and jar file
- Right click jar file and select run
- IntelliJ should auto assign as current config from then on and you can rebuild and run via top nav buttons

## JUnit testing

We use the JUnit framework for unit testing our codebase

### VSCode

As long as steps for build have been followed above, you should be able to run directly in editor by:

- Navigating to test file
- Click `play' icon on left side

Alternatively, to run all tests from command line

- Go to server project root
- Run `mvn '-Dtest=com.server.*Test' test`

### IntelliJ

As long as steps for build have been followed above, you should be able to run directly in editor by:

- Navigating to test file
- Click `play' icon on left side

With both server and client running locally, user can input commands to interact with the service

Some commands require command line arguments to function in the format `COMMAND argument`

Commands are case sensitive (must be uppercase)

## Available functionality

| Command        | Arguments                                   | Functionality                                                                                                                                                                                                                                                       |
|----------------|---------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| SHOWMYACCOUNTS | n/a                                         | Outputs all accounts owned by user stored in the system and their respective balance                                                                                                                                                                                |
| NEWACCOUNT     | accountType (`Main`, `Savings`, `Checking`) | Creates a new account for the user in the system, outputs `SUCCESS` or `FAIL` (user may only have one type of each account)                                                                                                                                         |
| MOVE           | MOVE(`Main`, `Savings`, 100)                | Takes in three arguments, the first is the account money is transferred out of. The second is the account money is transferred in. The last is the amount you'd like to transfer                                                                                    |
| PAY            | PAY(`Main`, `John`, `Checking`, 100)        | Takes in four arguments, the first is the account the money is transferred out of. The second is the username of the user you'd like to pay, the third is the account of theirs that money will be transferred in to. The last is the amount you'd like to transfer |
| QUIT           | n/a                                         | Quits the program                                                                                                                                                                                                                                                   |

package newbank.server.src.main;

public class Password {
    private String password;

    public Password(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}

package test.elias.ChatGod.model;

public class RegisteredUser {
    private String username;
    private String password;
    private String email;

    public RegisteredUser() {
    }

    public RegisteredUser(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() { return email; }

}

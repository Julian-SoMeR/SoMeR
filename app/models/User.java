package models;

/* Class have registered and logged in users. */
public class User {

    protected String username;
    protected String email;
    protected String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
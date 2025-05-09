package QUT.CAB302.fortunecookie;

// A class to represent user's credentials
public class User {

    // Fields to store user variables
    private String username;
    private String password;

    // Constructor to create a new user.
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    // Setters and getters for username and password
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}

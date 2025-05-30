package QUT.CAB302.fortunecookie.Model;

/**
 * A class to represent a user's credentials and identity.
 * <p>
 * Stores the username, password, and a unique user ID.
 * </p>
 */
public class User {

    private String username;
    private String password;
    private int id;

    /**
     * Constructs a new {@code User} with the specified username and password.
     *
     * @param username the user's username
     * @param password the user's password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the unique ID of the user.
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the user.
     *
     * @param id the new user ID
     */
    public void setId(int id) {
        this.id = id;
    }

}

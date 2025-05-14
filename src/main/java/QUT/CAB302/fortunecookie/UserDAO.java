package QUT.CAB302.fortunecookie;

// Database interface, defines methods for interacting with the database
public interface UserDAO {

    // Registers a new user with given username and password
    boolean registerUser(String username, String password, String email, String phone, String hours, String expertise);

    // Attempts to log in a user with given username and password
    User loginUser(String username, String password);


}

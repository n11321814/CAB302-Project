package QUT.CAB302.fortunecookie;

// Singleton access point for the UserDAO implementation, ensures there is only one instance of the database in use
public class UserDAOInstance {

    // Static instance of the database
    private static UserDAO instance = new UserDAODatabase();

    // Returns the shared instance
    public static UserDAO getInstance() {
        return instance;
    }
}

package QUT.CAB302.fortunecookie.Model;

/**
 * Singleton style provider for the {@link UserDAO} implementation.
 * <p>
 * Ensures a shared instance of {@code UserDAODatabase} is used.
 * </p>
 */
public class UserDAOInstance {

    private static UserDAO instance = new UserDAODatabase();

    /**
     * Returns the shared instance of {@code UserDAO}.
     *
     * @return the singleton {@code UserDAO} instance
     */
    public static UserDAO getInstance() {
        return instance;
    }
}

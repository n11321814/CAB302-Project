package QUT.CAB302.fortunecookie.Model;

/**
 * Data Access Object (DAO) interface for user-related operations.
 * <p>
 * Provides an abstraction for user registration and authentication.
 * Handles database interaction details.
 * </p>
 */
public interface UserDAO {

    /**
     * Registers a new user with the specified credentials and profile information.
     *
     * @param username  the desired username
     * @param password  the user's chosen password
     * @param email     the user's email address
     * @param phone     the user's phone number
     * @param hours     average hours of study per week
     * @param expertise the user's level of study expertise
     * @return {@code true} if registration is successful; {@code false} if the user already exists or fails validation
     */
    boolean registerUser(String username, String password, String email, String phone, String hours, String expertise);

    /**
     * Attempts to log in a user by verifying the provided credentials.
     *
     * @param username the username of the user
     * @param password the password to verify
     * @return a {@code User} object if authentication succeeds; {@code null} otherwise
     */
    User loginUser(String username, String password);
}

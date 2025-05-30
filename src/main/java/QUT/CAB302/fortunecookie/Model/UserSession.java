package QUT.CAB302.fortunecookie.Model;

/**
 * Utility class for managing the current user's session state.
 * <p>
 * Stores the user ID of the logged-in user for use across the application.
 * </p>
 */
public class UserSession {
    private static int userId;

    /**
     * Sets the ID of the currently logged-in user.
     *
     * @param id the user ID to store in the session
     */
    public static void setUserId(int id) {
        userId = id;
    }

    /**
     * Retrieves the ID of the currently logged-in user.
     *
     * @return the stored user ID
     */
    public static int getUserId() {
        return userId;
    }
}

package Java;

import QUT.CAB302.fortunecookie.Model.SQLiteConnection;
import QUT.CAB302.fortunecookie.Model.User;
import QUT.CAB302.fortunecookie.Model.UserDAO;
import QUT.CAB302.fortunecookie.Model.UserDAODatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link UserDAO} interface and its {@link UserDAODatabase} implementation.
 * <p>
 * These tests verify the correctness of user registration and login logic,
 * including edge cases such as duplicate usernames, incorrect credentials,
 * and handling of empty input fields.
 * </p>
 */
public class UserDAOTest {

    // Mock user data for testing
    private static final String username1 = "TestUser1";
    private static final String password1 = "TestPassword1";
    private static final String username2 = "TestUser2";
    private static final String password2 = "TestPassword2";
    private static final String email1 = "test@email.com";
    private static final String phone1 = "0486726574";
    private static final String hours1 = "1-5";
    private static final String expertise1 = "Beginner";




    // DAO instance under testing
    private UserDAO userDAO;

    /**
     * Sets up the database connection and clears tables before each test.
     * Ensures a clean state to avoid interference between tests.
     */
    @BeforeEach
    public void setUp() {
        userDAO = new UserDAODatabase(); // Tests against Mock Database as opposed to UserDAODatabase (SQL)

        try {
            Connection conn = SQLiteConnection.getInstance();
            Statement stmt = conn.createStatement();

            // Clear dependent tables first due to foreign key constraints
            stmt.executeUpdate("DELETE FROM savedQuotes");
            stmt.executeUpdate("DELETE FROM studyHabits");
            stmt.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests that a user can be successfully registered with valid credentials.
     * Ensures that the registerUser method returns true when data is valid.
     */
    @Test
    public void testRegisterUser() {
        String username = generateRandomString();
        boolean result = userDAO.registerUser(username, username, email1, phone1, hours1, expertise1);
        assertTrue(result, "User successfully registered");
    }
    /**
     * Tests that registering a user with a duplicate username fails.
     * Verifies that the method returns false when trying to register the same username twice.
     */
    @Test
    public void testDuplicateUsers() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        boolean result = userDAO.registerUser(username1, password2, email1, phone1, hours1, expertise1);
        assertFalse(result, "Duplicate Username should fail to register");
    }

    /**
     * Tests that a registered user can successfully log in with correct credentials.
     * Asserts the user is not null and the username matches.
     */
    @Test
    public void testLoginUser() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username1, password1);
        assertNotNull(user, "User should be logged in");
        assertEquals(username1, user.getUsername());
    }

    /**
     * Tests that logging in with a correct username but incorrect password fails.
     * Ensures that loginUser returns null in this case.
     */
    @Test
    public void testWrongPassword() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username1, password2);
        assertNull(user, "Login with wrong password should fail");
    }

    /**
     * Tests that login works after a user has been registered.
     * Ensures the system can retrieve a user that exists.
     */
    @Test
    public void testUserExists() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username1, password1);
        assertNotNull(user, "Login after registering should pass");
    }

    /**
     * Tests that login fails for a user who has not been registered.
     * Ensures loginUser returns null when the user doesn't exist in the database.
     */
    @Test
    public void testUserNotExists() {
        User user = userDAO.loginUser("dsahdhj", "asDasasda");
        assertNull(user, "Login without registering should fail");
    }

    /**
     * Tests that the system does not allow registration with empty input fields.
     * Ensures that registerUser returns false when provided with blank strings.
     */
    @Test
    public void testRegisterUserWithEmptyFields() {
        boolean result = userDAO.registerUser("", "", "", "", "", "");
        assertFalse(result, "User should not be registered with empty fields");
    }

    /**
     * Utility method to generate a random alphanumeric string.
     * Used to ensure unique usernames during test registration.
     *
     * @return a randomly generated 7-character string
     */
    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        long seed = System.currentTimeMillis(); // Using system time as seed
        Random random = new Random(seed);


        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();

    }
}

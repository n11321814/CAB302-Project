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

// Class containing unit tests for the login and registration logic
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

    // Initialises a fresh instance of the database before each test
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

    // Tests a user can successfully be registered
    @Test
    public void testRegisterUser() {
        String username = generateRandomString();
        boolean result = userDAO.registerUser(username, username, email1, phone1, hours1, expertise1);
        assertTrue(result, "User successfully registered");
    }

    // Tests that duplicate usernames cannot be created
    @Test
    public void testDuplicateUsers() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        boolean result = userDAO.registerUser(username1, password2, email1, phone1, hours1, expertise1);
        assertFalse(result, "Duplicate Username should fail to register");
    }

    // Tests that users can successfully log in
    @Test
    public void testLoginUser() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username1, password1);
        assertNotNull(user, "User should be logged in");
        assertEquals(username1, user.getUsername());
    }

    // Tests that logging in with incorrect password fails
    @Test
    public void testWrongPassword() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username1, password2);
        assertNull(user, "Login with wrong password should fail");
    }

    // Tests logging in with an existing user passes
    @Test
    public void testUserExists() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username1, password1);
        assertNotNull(user, "Login after registering should pass");
    }

    // Tests logging in with a non-existent user fails
    @Test
    public void testUserNotExists() {
        User user = userDAO.loginUser("dsahdhj", "asDasasda");
        assertNull(user, "Login without registering should fail");
    }

    @Test
    public void testRegisterUserWithEmptyFields() {
        boolean result = userDAO.registerUser("", "", "", "", "", "");
        assertFalse(result, "User should not be registered with empty fields");
    }

    @Test
    public void testRegisterDuplicateUser() {
        userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        boolean result = userDAO.registerUser(username1, password1, email1, phone1, hours1, expertise1);
        assertFalse(result, "Duplicate user should not be registered");
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        userDAO.registerUser(username2, password2, email1, phone1, hours1, expertise1);
        User user = userDAO.loginUser(username2, "WrongPassword");
        assertNull(user, "Login should fail with incorrect password");
    }

    @Test
    public void testLoginWithNonExistentUser() {
        User user = userDAO.loginUser("ghostuser", "ghostpass");
        assertNull(user, "Login should fail for non-existent user");
    }

    @Test
    public void testLoginAfterSuccessfulRegistration() {
        boolean success = userDAO.registerUser(username2, password2, email1, phone1, hours1, expertise1);
        assertTrue(success, "Registration should succeed");

        User user = userDAO.loginUser(username2, password2);
        assertNotNull(user, "User should be able to login after registration");
        assertEquals(username2, user.getUsername(), "Username should match");
    }

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

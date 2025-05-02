import QUT.CAB302.fortunecookie.User;
import QUT.CAB302.fortunecookie.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Class containing unit tests for the login and registration logic
public class UserDAOTest {

    // Mock user data for testing
    private static final String username1 = "TestUser1";
    private static final String password1 = "TestPassword1";
    private static final String username2 = "TestUser2";
    private static final String password2 = "TestPassword2";

    // DAO instance under testing
    private UserDAO userDAO;

    // Initialises a fresh instance of the database before each test
    @BeforeEach
    public void setUp(){
        userDAO = new MockUserDAO(); // Tests against Mock Database as opposed to UserDAODatabase (SQL)
    }

    // Tests a user can successfully be registered
    @Test
    public void testRegisterUser(){
        boolean result = userDAO.registerUser(username1, password1);
        assertTrue(result, "User successfully registered");
    }

    // Tests that duplicate usernames cannot be created
    @Test
    public void testDuplicateUsers(){
        userDAO.registerUser(username1, password1);
        boolean result = userDAO.registerUser(username1, password2);
        assertFalse(result, "Duplicate Username should fail to register");
    }

    // Tests that users can successfully log in
    @Test
    public void testLoginUser(){
        userDAO.registerUser(username1, password1);
        User user = userDAO.loginUser(username1, password1);
        assertNotNull(user, "User should be logged in");
        assertEquals(username1, user.getUsername());
    }

    // Tests that logging in with incorrect password fails
    @Test
    public void testWrongPassword(){
        userDAO.registerUser(username1, password1);
        User user = userDAO.loginUser(username1, password2);
        assertNull(user, "Login with wrong password should fail");
    }

    // Tests that logging in with an unregistered user fails
    @Test
    public void testUserNonExistent(){
        User user = userDAO.loginUser(username1, password1);
        assertNull(user, "Login without registering should fail");
    }
}

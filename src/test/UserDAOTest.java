import QUT.CAB302.fortunecookie.User;
import QUT.CAB302.fortunecookie.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private static final String username1 = "TestUser1";
    private static final String password1 = "TestPassword1";
    private static final String username2 = "TestUser2";
    private static final String password2 = "TestPassword2";
    private UserDAO userDAO;
    @BeforeEach
    public void setUp(){
        userDAO = new MockUserDAO(); // Tests against Mock Database as opposed to UserDAODatabase (SQL)
    }

    @Test
    public void testRegisterUser(){
        boolean result = userDAO.registerUser(username1, password1);
        assertTrue(result, "User successfully registered");
    }

    @Test
    public void testDuplicateUsers(){
        userDAO.registerUser(username1, password1);
        boolean result = userDAO.registerUser(username1, password2);
        assertFalse(result, "Duplicate Username should fail to register");
    }

    @Test
    public void testLoginUser(){
        userDAO.registerUser(username1, password1);
        User user = userDAO.loginUser(username1, password1);
        assertNotNull(user, "User should be logged in");
        assertEquals(username1, user.getUsername());
    }

    @Test
    public void testWrongPassword(){
        userDAO.registerUser(username1, password1);
        User user = userDAO.loginUser(username1, password2);
        assertNull(user, "Login with wrong password should fail");
    }

    @Test
    public void testUserNonExistent(){
        User user = userDAO.loginUser(username1, password1);
        assertNull(user, "Login without registering should fail");
    }
}

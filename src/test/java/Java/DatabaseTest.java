package Java;

import QUT.CAB302.fortunecookie.Model.SQLiteConnection;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test class for verifying basic database connectivity using {@link SQLiteConnection}.
 */
public class DatabaseTest {

    /**
     * Tests whether a connection to the SQLite database can be successfully established.
     * <p>
     * This ensures that the singleton instance of {@link SQLiteConnection} returns a valid connection object.
     * </p>
     */
    @Test
    public void testConnection() {
        Connection conn = SQLiteConnection.getInstance();
        assertEquals(true, conn != null);
    }
}

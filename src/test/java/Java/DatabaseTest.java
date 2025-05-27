package Java;

import QUT.CAB302.QuoteMe.model.SQLiteConnection;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DatabaseTest {
    @Test
    public void testConnection() {
        Connection conn = SQLiteConnection.getInstance();
        assertEquals(true, conn != null);
    }
}

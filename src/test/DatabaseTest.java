import QUT.CAB302.fortunecookie.*;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DatabaseTest {
    @Test
    public void testConnection() {
        Connection conn = SqliteConnection.getInstance();
        assertEquals(true, conn != null);
    }
}

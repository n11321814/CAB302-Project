package QUT.CAB302.fortunecookie.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton-style utility class for managing a single SQLite database connection.
 * <p>
 * Ensures that only one {@code Connection} instance is created and reused throughout the application.
 * The connection targets the {@code users.db} SQLite database.
 * </p>
 */
public class SQLiteConnection {
    private static Connection instance = null;

    /**
     * Initializes the SQLite database connection to {@code users.db}.
     * <p>
     * Called internally when the connection instance is first requested.
     * Logs any SQL exceptions to standard error output.
     * </p>
     */
    public SQLiteConnection() {
        String url = "jdbc:sqlite:users.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Returns the singleton {@code Connection} instance to the SQLite database.
     * <p>
     * If the connection has not yet been established, it will be created on demand.
     * </p>
     *
     * @return the shared {@code Connection} instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SQLiteConnection();
        }
        return instance;
    }
}
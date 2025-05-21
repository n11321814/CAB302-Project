package QUT.CAB302.fortunecookie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that provides a connection to the SQLite database.
 * Ensures that only one instance of the database connection is created
 * and used throughout the application.
 */
public class SQLiteConnection {

    // The single instance of the SQLite database connection
    private static Connection instance = null;

    /**
     * Private constructor that initializes the connection to the SQLite database.
     * The connection is established to the "users.db" database.
     */
    public SQLiteConnection() {
        // Database URL for SQLite
        String url = "jdbc:sqlite:users.db";
        try {
            // Attempt to establish a connection to the SQLite database
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            // If an error occurs, print the exception
            System.err.println(sqlEx);
        }
    }

    /**
     * Provides access to the single instance of the SQLite database connection.
     * If the connection is not already created, it initializes it.
     *
     * @return The instance of the SQLite database connection
     */
    public static Connection getInstance() {
        // Create the connection instance if it does not exist
        if (instance == null) {
            new SQLiteConnection();
        }
        return instance;  // Return the existing connection instance
    }
}

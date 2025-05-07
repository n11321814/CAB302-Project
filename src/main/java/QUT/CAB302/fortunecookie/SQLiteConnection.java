package QUT.CAB302.fortunecookie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static Connection instance = null;

    public SQLiteConnection() {
        String url = "jdbc:sqlite:users.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new SQLiteConnection();
        }
        return instance;
    }
}
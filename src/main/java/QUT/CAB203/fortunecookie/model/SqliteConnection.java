package QUT.CAB203.fortunecookie.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class that provides a connection to the SQLite database

public class SqliteConnection {
    private static Connection instance = null;

    private SqliteConnection() {
        String url = "jdbc:sqlite:contacts.db";
        try{
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    public static Connection getInstance(){
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
        }
    }


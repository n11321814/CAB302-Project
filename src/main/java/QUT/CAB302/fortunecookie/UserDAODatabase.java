package QUT.CAB302.fortunecookie;

import java.sql.*;

// Implementation of the UserDAO interface using SQLite
public class UserDAODatabase implements UserDAO {
    private static final String DB_URL = "jdbc:sqlite:";

    private Connection connection;

    // Database file path
    private static final String DB_URL = "jdbc:sqlite:users.db";

    // Constructor for the user table
    public UserDAODatabase() {
        connection = SQLiteConnection.getInstance();
        createUserTable();
    }

    // Creates the user table if it doesn't already exist, stores username and password
    private void createUserTable() {
        try {
             Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Registers a user by inserting their credentials into the database
    @Override
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Authenticates users logging in against the database
    @Override
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
             PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return null;
    }
}
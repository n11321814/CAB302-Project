package QUT.CAB302.fortunecookie;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

// Implementation of the UserDAO interface using SQLite
public class UserDAODatabase implements UserDAO {

    // Database file path
    private static final String DB_URL = "jdbc:sqlite:";

    private Connection connection;

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
                    "password TEXT NOT NULL," +
                    "phone TEXT," +
                    "email TEXT," +
                    "hoursOfStudy INTEGER," +
                    "studyStreak INTEGER," +
                    "expertiseLevel TEXT," +
                    "CHECK (phone IS NOT NULL OR email IS NOT NULL)" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Registers a user by inserting their credentials into the database
    @Override
    public boolean registerUser(String username, String password, String email, String phone, String hoursOfStudy, String expertiseLevel) {

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hashes Password

        String sql = "INSERT INTO users(username, password, email, phone, hoursOfStudy, expertiseLevel) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, hoursOfStudy);
            pstmt.setString(6, expertiseLevel);
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
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password"); // Checks hashed password
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(username, storedHash);
                }
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return null;
    }
}
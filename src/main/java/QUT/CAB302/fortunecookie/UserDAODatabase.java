package QUT.CAB302.fortunecookie;

import java.sql.*;
public class UserDAODatabase implements UserDAO {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public UserDAODatabase() {
        createUserTable();
    }

    private void createUserTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "phone TEXT," +
                    "email TEXT," +
                    "CHECK (phone IS NOT NULL OR email IS NOT NULL))";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean registerUser(String username, String password, String email, String phone) {
        String sql = "INSERT INTO users(username, password, email, phone) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }
    @Override
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
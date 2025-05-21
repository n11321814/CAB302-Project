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
        createStudyHabitsTable();
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
                    "CHECK (phone IS NOT NULL OR email IS NOT NULL)" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createStudyHabitsTable() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS studyHabits (" +
                    "id INTEGER PRIMARY KEY," +
                    "hoursOfStudy INTEGER," +
                    "studyStreak INTEGER," +
                    "expertiseLevel TEXT," +
                    "FOREIGN KEY(id) REFERENCES users(id)" +
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


        try {
            // Ensures that the insert transactions fails or succeeds together
            connection.setAutoCommit(false);

            String userSql = "INSERT INTO users(username, password, email, phone) VALUES(?, ?, ?, ?)";
            PreparedStatement userStmt = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, username);
            userStmt.setString(2, hashedPassword);
            userStmt.setString(3, email);
            userStmt.setString(4, phone);
            userStmt.executeUpdate();

            // Retrieve the created users ID
            ResultSet rs = userStmt.getGeneratedKeys();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                throw new SQLException("User ID retrieval failed.");
            }

            // Insert study habits into studyHabits Table
            String habitSql = "INSERT INTO studyHabits (id, hoursOfStudy, expertiseLevel) VALUES (?, ?, ?)";
            PreparedStatement habitStmt = connection.prepareStatement(habitSql);
            habitStmt.setInt(1, userId);
            habitStmt.setString(2, hoursOfStudy);
            habitStmt.setString(3,expertiseLevel);
            habitStmt.executeUpdate();

            // Commit the transactions
            connection.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;

        } finally {
            try {
                connection.setAutoCommit(true); // Restore the default commit behaviour
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
                    // Build the full user object
                    User user = new User(username, storedHash);
                    user.setId(rs.getInt("id")); // Set user Id from DB
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return null;
    }
}
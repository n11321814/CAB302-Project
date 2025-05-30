package QUT.CAB302.fortunecookie.Model;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Implementation of {@link UserDAO} that interacts with a SQLite database.
 * <p>
 * Handles user registration, authentication, and related table creation including
 * user credentials, study habits, and saved quotes.
 * </p>
 */
public class UserDAODatabase implements UserDAO {

    private static final String DB_URL = "jdbc:sqlite:";

    private Connection connection;

    /**
     * Initializes the database connection and ensures required tables are created.
     */
    public UserDAODatabase() {
        connection = SQLiteConnection.getInstance();
        createUserTable();
        createStudyHabitsTable();
        createSavedQuotesTable();
    }

    /**
     * Creates the {@code users} table if it does not already exist.
     * <p>
     * Stores user credentials and contact details. Enforces a constraint
     * requiring at least one contact method (email or phone).
     * </p>
     */
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

    /**
     * Creates the {@code studyHabits} table if it does not already exist.
     * <p>
     * Stores hours of study, study streaks, and expertise level for each user.
     * Linked to the {@code users} table via a foreign key.
     * </p>
     */
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

    /**
     * Creates the {@code savedQuotes} table if it does not already exist.
     * <p>
     * Stores user-saved motivational quotes, using a composite primary key
     * to prevent duplicate quote entries per user.
     * </p>
     */
    private void createSavedQuotesTable() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS savedQuotes (" +
                    "id INTEGER," +
                    "savedQuote TEXT NOT NULL," +
                    "PRIMARY KEY(id, savedQuote)," +
                    "FOREIGN KEY(id) REFERENCES users(id)" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a new user and stores their study habits in the database.
     * <p>
     * Performs the operation as a transaction to ensure consistency between
     * {@code users} and {@code studyHabits} tables.
     * </p>
     *
     * @param username the new user's username
     * @param password the new user's plain text password (will be hashed)
     * @param email the user's email address
     * @param phone the user's phone number
     * @param hoursOfStudy average hours studied per week
     * @param expertiseLevel the user's self-reported expertise level
     * @return {@code true} if registration succeeds, {@code false} otherwise
     */
    @Override
    public boolean registerUser(String username, String password, String email, String phone, String hoursOfStudy, String expertiseLevel) {

        if (username == null || username.isBlank() ||
                password == null || password.isBlank() ||
                (email == null || email.isBlank()) && (phone == null || phone.isBlank()) ||
                hoursOfStudy == null || hoursOfStudy.isBlank() ||
                expertiseLevel == null || expertiseLevel.isBlank()) {
            System.out.println("Registration failed: One or more fields are empty or invalid.");
            return false;
        }

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

    /**
     * Authenticates a user by comparing the provided password to the stored hashed password.
     *
     * @param username the user's username
     * @param password the user's plain text password to verify
     * @return a {@code User} object if authentication is successful; {@code null} otherwise
     */
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
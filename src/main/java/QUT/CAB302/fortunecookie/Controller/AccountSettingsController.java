package QUT.CAB302.fortunecookie.Controller;

import QUT.CAB302.fortunecookie.ApplicationMain;
import QUT.CAB302.fortunecookie.Model.SQLiteConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller class for managing user account settings.
 * Handles user profile updates, password changes, and page navigation.
 */
public class AccountSettingsController {
    @FXML
    private Label usernameLabel;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> hoursComboBox;
    @FXML
    private ComboBox<String> expertiseComboBox;
    @FXML
    private Label statusLabel;
    @FXML
    private Button backToHome;

    private int userId;

    /**
     * Initializes the account settings page with data for the specified user.
     *
     * @param userID the ID of the logged-in user
     */
    public void initialiseUser(int userID) {
        this.userId = userID;
        loadUserData();
    }

    /**
     * Loads the user's account and study habit data from the database.
     * <p>
     * Retrieves the username, email, and phone number from the `users` table,
     * and the study hours and expertise level from the `studyHabits` table,
     * then populates the corresponding UI fields.
     * </p>
     */
    private void loadUserData() {
        try {
            Connection conn = SQLiteConnection.getInstance();

            // Load user info
            String userSql = "SELECT username, email, phone FROM users WHERE id = ?";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setInt(1, userId);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                usernameLabel.setText(userRs.getString("username"));
                emailField.setText(userRs.getString("email"));
                phoneField.setText(userRs.getString("phone"));
            }

            // Load study habits
            String habitSql = "SELECT hoursOfStudy, expertiseLevel FROM studyHabits WHERE id = ?";
            PreparedStatement habitStmt = conn.prepareStatement(habitSql);
            habitStmt.setInt(1, userId);
            ResultSet habitRs = habitStmt.executeQuery();

            if (habitRs.next()) {
                hoursComboBox.setValue(habitRs.getString("hoursOfStudy"));
                expertiseComboBox.setValue(habitRs.getString("expertiseLevel"));
            }

        } catch (SQLException e) {
            statusLabel.setText("Failed to load account data.");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    /**
     * Handles saving the user's updated account and study habit information.
     * <p>
     * This method updates the user's email and phone number in the `users` table,
     * their study preferences in the `studyHabits` table, and optionally changes
     * the user's password if the password fields are filled and validated.
     * </p>
     *
     * <ul>
     *   <li>Validates that all password fields are filled if any are used.</li>
     *   <li>Ensures new password and confirmation match.</li>
     *   <li>Verifies the current password against the hashed password in the database.</li>
     * </ul>
     *
     * On success, updates are saved to the database and a success message is displayed.
     * On failure, appropriate error messages are shown in the status label.
     */
    @FXML
    private void handleSaveChanges() {
        try {
            Connection conn = SQLiteConnection.getInstance();

            // Update email and phone
            String userUpdate = "UPDATE users SET email = ?, phone = ? WHERE id = ?";
            PreparedStatement userStmt = conn.prepareStatement(userUpdate);
            userStmt.setString(1, emailField.getText());
            userStmt.setString(2, phoneField.getText());
            userStmt.setInt(3, userId);
            userStmt.executeUpdate();

            // Update study habits
            String habitUpdate = "UPDATE studyHabits SET hoursOfStudy = ?, expertiseLevel = ? WHERE id = ?";
            PreparedStatement habitStmt = conn.prepareStatement(habitUpdate);
            habitStmt.setString(1, hoursComboBox.getValue());
            habitStmt.setString(2, expertiseComboBox.getValue());
            habitStmt.setInt(3, userId);
            habitStmt.executeUpdate();

            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!currentPassword.isEmpty() || !newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    statusLabel.setText("Fill in all password fields to change your password.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    statusLabel.setText("New passwords do not match.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }


                // Verify current password
                String fetchPasswordSql = "SELECT password FROM users WHERE id = ?";
                PreparedStatement fetchPwdStmt = conn.prepareStatement(fetchPasswordSql);
                fetchPwdStmt.setInt(1, userId);
                ResultSet pwdRs = fetchPwdStmt.executeQuery();

                if (pwdRs.next()) {
                    String storedHashedPassword = pwdRs.getString("password");
                    if (!BCrypt.checkpw(currentPassword, storedHashedPassword)) {
                        statusLabel.setText("Current password is incorrect.");
                        statusLabel.setStyle("-fx-text-fill: red;");
                        return;
                    }
                }

                // Hash and update new password
                String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                String passwordUpdate = "UPDATE users SET password = ? WHERE id = ?";
                PreparedStatement pwdStmt = conn.prepareStatement(passwordUpdate);
                pwdStmt.setString(1, hashed);
                pwdStmt.setInt(2, userId);
                int rows = pwdStmt.executeUpdate();
                System.out.println("Password updated? " + (rows > 0));
            }

            statusLabel.setText("Changes saved succssfully.");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Clear password fields
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmPasswordField.clear();

        } catch (SQLException e) {
            statusLabel.setText("Failed to save changes");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    /**
     * Navigates the user back to the homepage scene.
     * <p>
     * Loads the Homepage FXML layout and sets it as the current scene in the stage.
     * Triggered by the "Back to Home" button.
     * </p>
     *
     * IOException if the FXML file cannot be loaded
     */
    @FXML
    private void goToHomepage() {
        try {
            Stage stage = (Stage) backToHome.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

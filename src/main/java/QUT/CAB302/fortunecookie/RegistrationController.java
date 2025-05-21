package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the registration view, handling user interaction, registration logic,
 * and navigation to the login screen upon successful registration.
 */
public class RegistrationController {

    // FXML UI elements for user input and navigation
    @FXML
    private TextField usernameField;  // Text field for the username input
    @FXML
    private PasswordField passwordField;  // Password field for the password input
    @FXML
    private Button toLogin;  // Button to navigate back to the login screen

    // Access to the database
    private UserDAO userDAO = UserDAOInstance.getInstance();  // Instance of UserDAO for DB interaction

    /**
     * Handles the registration logic. Verifies whether the username and password fields are filled.
     * If successful, the user is registered and an alert is shown. If the registration fails,
     * an error message is displayed.
     *
     * @throws IOException if an error occurs while loading the login scene after registration
     */
    @FXML
    private void handleRegister() throws IOException {
        // Retrieve the entered username and password from the UI
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please fill all fields");  // Show an alert if either field is empty
        } else {
            // Attempt to register the user in the database
            boolean success = userDAO.registerUser(username, password);

            if (success) {
                // Show a success message and navigate to the login screen
                showAlert("Registration successful");
                goToLogin();
            } else {
                // Show an error message if the username already exists
                showAlert("User already exists");
            }
        }
    }

    /**
     * Handles navigation to the login screen when the "Back to Login" button is clicked.
     *
     * @throws IOException if an error occurs while loading the login scene
     */
    @FXML
    private void goToLogin() throws IOException {
        // Navigate back to the login screen
        Stage stage = (Stage) toLogin.getScene().getWindow();  // Get the current window
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);  // Set the new scene with the login screen
    }

    /**
     * Utility method to show an informational alert with a given message.
     *
     * @param message The message to display in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");  // Title of the alert window
        alert.setHeaderText(null);  // Header for the alert
        alert.setContentText(message);  // Set the content message
        alert.showAndWait();  // Display the alert and wait for the user to dismiss it
    }
}

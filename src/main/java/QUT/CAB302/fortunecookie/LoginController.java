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
 * Controller for the login view, handling user interaction, authentication, and navigation
 * to the homepage or registration screen.
 */
public class LoginController {

    // FXML UI elements for user input and navigation
    @FXML
    private TextField usernameField;  // Text field for the username input
    @FXML
    private PasswordField passwordField;  // Password field for the password input
    @FXML
    private Button toRegister;  // Button to navigate to the registration screen
    @FXML
    private Button toHomepage;  // Button to navigate to the homepage after successful login

    // Access to the database
    private UserDAO userDAO = UserDAOInstance.getInstance();  // Instance of UserDAO for DB interaction

    /**
     * Handles the login logic. Authenticates the user using the provided username and password.
     * If successful, navigates to the homepage. Otherwise, shows a failure alert.
     *
     * @throws IOException if an error occurs while loading the homepage scene
     */
    @FXML
    private void handleLogin() throws IOException {
        // Retrieve the entered username and password from the UI
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Attempt to authenticate the user
        User user = userDAO.loginUser(username, password);

        if (user != null) {
            // Show a success message if login is successful
            showAlert("Login successful");

            // Navigate to the homepage
            Stage stage = (Stage) toHomepage.getScene().getWindow();  // Get the current window
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);  // Set the new scene with the homepage
        } else {
            // Show an error message if login fails
            showAlert("Login failed");
        }
    }

    /**
     * Handles navigation to the registration screen when the "Register" button is clicked.
     *
     * @throws IOException if an error occurs while loading the registration scene
     */
    @FXML
    private void goToRegister() throws IOException {
        // Navigate to the registration screen
        Stage stage = (Stage) toRegister.getScene().getWindow();  // Get the current window
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);  // Set the new scene with the registration screen
    }

    /**
     * Utility method to show an informational alert with a given message.
     *
     * @param message The message to display in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");  // Title of the alert window
        alert.setHeaderText(null);  // No header for the alert
        alert.setContentText(message);  // Set the content message
        alert.showAndWait();  // Display the alert and wait for the user to dismiss it
    }
}

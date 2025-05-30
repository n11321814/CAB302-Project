package QUT.CAB302.fortunecookie.Controller;

import QUT.CAB302.fortunecookie.*;
import QUT.CAB302.fortunecookie.Model.User;
import QUT.CAB302.fortunecookie.Model.UserDAO;
import QUT.CAB302.fortunecookie.Model.UserDAOInstance;
import QUT.CAB302.fortunecookie.Model.UserSession;
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
 * Controller class for the login view.
 * <p>
 * Manages user interactions on the login screen, including authenticating users,
 * navigating to the registration page, and providing feedback via alerts.
 * </p>
 */
public class LoginController {

    // FXML UI elements
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private Button toRegister;
    @FXML
    private Button toHomepage;

    // Access to the database
    private UserDAO userDAO = UserDAOInstance.getInstance();

    /**
     * Handles the login process for a user.
     * <p>
     * Validates the entered username and password using the UserDAO.
     * If authentication succeeds, stores the user's ID in session and navigates to the homepage.
     * If it fails, displays an error alert.
     * </p>
     *
     * @throws IOException if the Homepage FXML file cannot be loaded
     */
    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userDAO.loginUser(username, password);
        if (user != null) {
            UserSession.setUserId(user.getId());
            showAlert("Login successful");
            Stage stage = (Stage) toHomepage.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } else {
            showAlert("Login failed");
        }
    }

    /**
     * Navigates the user to the registration screen.
     * <p>
     * Loads the Registration.fxml layout and sets it as the current scene.
     * Triggered when the user opts to create a new account.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void goToRegister() throws IOException {
        Stage stage = (Stage) toRegister.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Displays an informational alert dialog with the given message.
     *
     * @param message the message to display in the alert content
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

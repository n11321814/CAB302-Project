package QUT.CAB302.fortunecookie.Controller;

import QUT.CAB302.fortunecookie.ApplicationMain;
import QUT.CAB302.fortunecookie.Model.UserDAO;
import QUT.CAB302.fortunecookie.Model.UserDAOInstance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import java.io.IOException;

/**
 * Controller class for the registration view.
 * <p>
 * Handles user input validation, account creation, and navigation to the login screen.
 * Communicates with the database through the UserDAO.
 * </p>
 */
public class RegistrationController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> hoursComboBox;
    @FXML
    private ComboBox<String> expertiseComboBox;
    @FXML
    private Button toLogin;

    // Access to the database
    private UserDAO userDAO = UserDAOInstance.getInstance();

    /**
     * Handles the registration process for a new user.
     * <p>
     * Validates user input fields including username, password, contact info,
     * and study habit selections. If validation passes and registration is
     * successful, navigates the user to the login screen.
     * </p>
     *
     * @throws IOException if navigation to the login screen fails
     */
    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String hours = hoursComboBox.getValue();
        String expertise = expertiseComboBox.getValue();
        if (username.isEmpty() || password.isEmpty() || (email.isEmpty() && phone.isEmpty())) {
            showAlert("Please enter a username, password and at least an email or phone number.");
        } else if (hours == null || expertise == null) {
            showAlert("Please select your average hours studied per week and your level of expertise.");
        } else {
            boolean success = userDAO.registerUser(username, password, email, phone, hours, expertise);
            if(success){
                showAlert("Registration successful");
                goToLogin();
            } else{
                showAlert("User already exists");
            }
        }
    }

    /**
     * Navigates the user to the login screen.
     * <p>
     * Triggered when the "Back" or "Login" button is clicked after registration.
     * </p>
     *
     * @throws IOException if the Login.fxml file cannot be loaded
     */
    @FXML
    private void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Login.fxml"));
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


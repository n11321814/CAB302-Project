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

// Controller for the login view, handles user interaction and navigation on the login screen
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

    // Handles login logic, authenticates the user and navigates to the homepage if successful
    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userDAO.loginUser(username, password);
        if (user != null) {
            UserSession.setUserId(user.getId());
            showAlert("Login successful");
            Stage stage = (Stage) toHomepage.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } else {
            showAlert("Login failed");
        }
    }

    // Handles navigation to registration screen when register button is clicked
    @FXML
    private void goToRegister() throws IOException {
        Stage stage = (Stage) toRegister.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    // Utility method to show an alert with a message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

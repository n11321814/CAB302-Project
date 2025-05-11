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

// Controller for the registration view, handles user interaction and navigation on the registration screen
public class RegistrationController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button toLogin;

    // Access to the database
    private UserDAO userDAO = UserDAOInstance.getInstance();

    // Handles registration logic, determines whether username and password is valid and navigates to login page if successful
    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please fill all fields");
        } else {
            boolean success = userDAO.registerUser(username, password);
            if(success){
                showAlert("Registration successful");
                goToLogin();
            } else{
                showAlert("User already exists");
            }
        }
    }

    // Handles navigation to login screen when back button is clicked
    @FXML
    private void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
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


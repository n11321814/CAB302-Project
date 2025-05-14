package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.Connection;

public class AccountSettingsController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> hoursComboBox;
    @FXML
    private ComboBox<String> expertiseComboBox;

    private Connection connection;
    private String currentUserName;

}

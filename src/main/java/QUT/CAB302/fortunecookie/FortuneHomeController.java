package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * Controller for the Fortune Cookie homepage (fortune-home.fxml).
 */
public class FortuneHomeController {

    @FXML
    private Label progressLabel;

    @FXML
    private Label streakLabel;

    @FXML
    private Region graphPlaceholder;

    @FXML
    private Label toLogin;

    /**
     * Called by the FXML loader after all @FXML fields are injected.
     * Initialize default UI values here.
     */
    @FXML
    public void initialize() {
        progressLabel.setText("Progress: 0/10");
        streakLabel.setText("Study streak: 🔥 0");
    }

    /**
     * Handler when the user "breaks" the fortune cookie.
     * Shows a random fortune in an alert dialog.
     */
    @FXML
    private void onCookieClick(MouseEvent event) {
        String[] fortunes = {
            "Great things are coming your way!",
            "You will achieve your goals.",
            "A pleasant surprise is in store for you.",
            "Believe in yourself and others will too.",
            "Adventure can be real happiness."
        };
        // Pick a random fortune
        String fortune = fortunes[new Random().nextInt(fortunes.length)];

        // Display in an information alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your Fortune");
        alert.setHeaderText(null);
        alert.setContentText(fortune);
        alert.showAndWait();
    }

    @FXML
    public void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    // You can add additional @FXML methods here for other interactive elements
}

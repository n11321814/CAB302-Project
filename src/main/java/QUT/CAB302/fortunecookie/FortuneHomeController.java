package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Random;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class FortuneHomeController {

    @FXML
    private Label streakLabel;

    @FXML
    private Label quoteLabel;

    @FXML
    private Label toLogin;

    @FXML
    private Button cookieButton;

    @FXML
    private Button startStudyButton;

    /**
     * Called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Attempt to get the current user; fallback to default value
        // User currentUser = userDAO.getCurrentUser(); // Uncomment and implement this when ready

        if (streakLabel != null /* && currentUser != null */) {
            // int streak = currentUser.getStreak(); // Uncomment when ready
            // streakLabel.setText("Study streak: 🔥 " + streak);
            streakLabel.setText("Study streak: 🔥 0"); // Temporary placeholder
        }

        if (quoteLabel != null) {
            quoteLabel.setText("Quote: Stay focused and keep going!");
        }
    }

    /**
     * Handler for cookie break button.
     */
    @FXML
    private void onCookieClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // Simple animation
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), cookieButton);
            scale.setByX(0.2);
            scale.setByY(0.2);
            scale.setCycleCount(2);
            scale.setAutoReverse(true);

            FadeTransition fade = new FadeTransition(Duration.millis(500), cookieButton);
            fade.setFromValue(1.0);
            fade.setToValue(0.8);
            fade.setCycleCount(2);
            fade.setAutoReverse(true);

            scale.play();
            fade.play();

            // Show fortune message
            String[] fortunes = {
                    "Great things are coming your way!",
                    "You will achieve your goals.",
                    "A pleasant surprise is in store for you.",
                    "Believe in yourself and others will too.",
                    "Adventure can be real happiness."
            };
            String fortune = fortunes[new Random().nextInt(fortunes.length)];

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Your Fortune");
            alert.setHeaderText(null);
            alert.setContentText(fortune);
            alert.showAndWait();
        }
    }

    /**
     * Go back to login screen.
     */
    @FXML
    public void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Triggered when "Start Study Mode" is clicked.
     */
    @FXML
    private void startStudyMode() {
        // Create an Alert to ask the user if they want to start study mode
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Start Study Mode");
        alert.setHeaderText("Are you sure you want to start study mode?");
        alert.setContentText("You will be switched to the study page.");

        // Set custom button types (Yes / No)
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Show the alert and wait for user input
        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                // If user clicks "Yes", navigate to the Study Mode page
                try {
                    Stage stage = (Stage) toLogin.getScene().getWindow(); // Get the current stage
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studymode.fxml")); // Load the study mode FXML
                    Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT); // Create a new scene
                    stage.setScene(scene); // Set the new scene
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // If user clicks "No", just close the alert
                alert.close();
            }
        });
    }
}

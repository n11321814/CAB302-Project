package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.Node;

/**
 * Controller for the homepage view. Handles UI interactions such as
 * displaying quotes, navigating to other screens, and handling button animations.
 */
public class HomepageController {

    /** Displays the user's current study streak. */
    @FXML
    private Label streakLabel;

    /** Displays an inspirational quote. */
    @FXML
    private Label quoteLabel;

    /** Label used as a clickable link to return to the login screen. */
    @FXML
    private Label toLogin;

    /** Button that, when double-clicked, shows a fortune with animations. */
    @FXML
    private Button cookieButton;

    /** Button that starts the study mode. */
    @FXML
    private Button startStudyButton;

    /** Button that navigates to the Study Vault page. */
    @FXML
    public Button studyVaultButton;

    /**
     * Called automatically after the FXML is loaded.
     * Initializes UI components such as the quote and study streak.
     */
    @FXML
    public void initialize() {
        // Placeholder logic – replace with real user data once available
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }

        if (quoteLabel != null) {
            quoteLabel.setText("Quote: Stay focused and keep going!");
        }
    }

    /**
     * Handles double-clicks on the cookie button to show a fortune with animation.
     *
     * @param event the mouse event that triggered this handler
     */
    @FXML
    private void onCookieClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // Animate the cookie button
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

            // Show random fortune
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
     * Navigates the user back to the login screen.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Handles the "Start Study Mode" button click.
     * Asks for user confirmation and navigates to the study mode screen if confirmed.
     */
    @FXML
    private void startStudyMode() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Start Study Mode");
        alert.setHeaderText("Are you sure you want to start study mode?");
        alert.setContentText("You will be switched to the study page.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                try {
                    Stage stage = (Stage) toLogin.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studymode.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                alert.close();
            }
        });
    }

    /**
     * Navigates to the Study Vault screen when the corresponding button is clicked.
     *
     * @param event the mouse event that triggered this handler
     */
    @FXML
    private void goToStudyVault(MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudyVault.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Show error message to user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load Study Vault");
            alert.setContentText("Could not load the Study Vault page. Please try again.");
            alert.showAndWait();
        }
    }
}

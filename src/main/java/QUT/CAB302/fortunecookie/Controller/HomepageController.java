package QUT.CAB302.fortunecookie.Controller;

import QUT.CAB302.fortunecookie.ApplicationMain;
import QUT.CAB302.fortunecookie.Model.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

/**
 * Controller for the Homepage view.
 * Handles navigation, UI animations, and user session display.
 */
public class HomepageController {

    private Button cookieButton;
    @FXML private Label streakLabel;
    @FXML private Label quoteLabel;
    @FXML private Label toLogin;

    @FXML private Button toStudyMode;
    @FXML private Button toStudyVault;
    @FXML private Label toSettings;

    /**
     * Initializes the homepage by setting default values for UI components.
     * <p>
     * Displays the user's current study streak and a motivational quote.
     * Called automatically by JavaFX after the FXML components are loaded.
     * </p>
     */
    @FXML public void initialize() {
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 1");
        }
        if (quoteLabel != null) {
            quoteLabel.setText("The best way to predict the future is to create it");
        }
    }

    /**
     * Handles a double click event on the cookie button.
     * <p>
     * Triggers a scale and fade animation, then displays a random fortune message
     * in an alert dialog to provide a fun, interactive user experience.
     * </p>
     *
     * @param event the mouse event triggering the action (must be a double click)
     */
    @FXML private void onCookieClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
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
     * Navigates the user to the login screen.
     * <p>
     * Loads the Login.fxml layout and replaces the current scene with it.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML public void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Navigates the user to the Study Mode screen.
     * <p>
     * Loads the StudyMode.fxml layout and sets it as the current scene.
     * Triggered by the Study Mode button.
     * </p>
     */
    @FXML private void goToStudyMode() {
        try {
            Stage stage = (Stage) toStudyMode.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("StudyMode.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates the user to the Study Vault screen.
     * <p>
     * Loads the StudyVault.fxml layout and sets it as the current scene.
     * If loading fails, an error alert is displayed to the user.
     * </p>
     */
    @FXML private void goToStudyVault() {
        try {
            Stage stage = (Stage) toStudyVault.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("StudyVault.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load Study Vault");
            alert.setContentText("Could not load the Study Vault page. Please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Navigates the user to the Account Settings screen.
     * <p>
     * Loads the Settings.fxml layout, retrieves the associated controller,
     * and initializes it with the current user's ID from the session.
     * If the FXML fails to load, an error alert is shown.
     * </p>
     */
    @FXML private void goToSettings() {
        try {
            Stage stage = (Stage) toSettings.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Settings.fxml"));

            Parent root = fxmlLoader.load();
            AccountSettingsController controller = fxmlLoader.getController();
            controller.initialiseUser(UserSession.getUserId());

            Scene scene = new Scene(root, ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load Settings");
            alert.setContentText("Could not load the Settings page. Please try again.");
            alert.showAndWait();
        }
    }
}

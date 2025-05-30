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
import javafx.scene.Node;
import java.io.IOException;
import java.util.Random;

public class HomepageController {


    @FXML private Label streakLabel;
    @FXML private Label quoteLabel;
    @FXML private Label toLogin;

    @FXML private Button cookieButton;
    @FXML private Button toStudyMode;
    @FXML private Button toStudyVault;
    @FXML private Label toSettings;

    @FXML public void initialize() {
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }
        if (quoteLabel != null) {
            quoteLabel.setText("Quote: Stay focused and keep going!");
        }
    }

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

    @FXML public void goToLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    @FXML private void goToStudyMode(MouseEvent event) {
        try {
            Stage stage = (Stage) toStudyMode.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("StudyMode.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void goToStudyVault(MouseEvent event) {
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

    @FXML private void goToSettings(MouseEvent event) {
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

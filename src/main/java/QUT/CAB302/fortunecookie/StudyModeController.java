package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class StudyModeController {

    @FXML
    private Label timerLabel;  // Label for showing the timer

    @FXML
    private Button startButton;  // Button to start the session

    @FXML
    private TextField subjectTextField;  // TextField for the subject

    @FXML
    private TextField durationTextField;  // TextField for the study duration

    @FXML
    private Label streakLabel;  // Label for showing the study streak

    @FXML
    private Label toLogin;  // Logout button

    private boolean isSessionActive = false;  // Track if the session is active
    private Timeline timer;  // Timeline for the study timer
    private int minutes = 0;  // Minutes counter for the timer
    private int seconds = 0;  // Seconds counter for the timer
    private int totalTimeInSeconds = 0;  // Total study time in seconds

    /**
     * Called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Set the initial streak value
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }
    }

    /**
     * Start the study session after asking for duration and subject.
     */
    @FXML
    private void startStudySession(MouseEvent event) {
        String subject = subjectTextField.getText();
        String durationText = durationTextField.getText();

        if (subject.isEmpty() || durationText.isEmpty()) {
            showError("Please enter both subject and duration.");
            return;
        }

        try {
            totalTimeInSeconds = Integer.parseInt(durationText) * 60;  // Convert duration to seconds
            minutes = totalTimeInSeconds / 60;
            seconds = totalTimeInSeconds % 60;

            // Update the timer label with the initial time
            updateTimerDisplay();

            // Set up the timer to update every second
            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

            // Change button text to Pause
            startButton.setText("Pause Study Session");

            isSessionActive = true;
        } catch (NumberFormatException e) {
            showError("Invalid duration format. Please enter a valid number.");
        }
    }

    /**
     * Update the timer on each tick.
     */
    private void updateTimer() {
        if (totalTimeInSeconds > 0) {
            totalTimeInSeconds--;
            minutes = totalTimeInSeconds / 60;
            seconds = totalTimeInSeconds % 60;
            updateTimerDisplay();
        } else {
            timer.stop();
            showSessionEndPopup();
        }
    }

    private void updateTimerDisplay() {
        String time = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(time);
    }

    /**
     * Show an error message in case of invalid input.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show the session finished popup.
     */
    private void showSessionEndPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Session Finished");
            alert.setHeaderText("Study session is over.");
            alert.setContentText("Do you want to start a new session or end this one?");

            ButtonType newSession = new ButtonType("New Session");
            ButtonType end = new ButtonType("End");

            alert.getButtonTypes().setAll(newSession, end);

            alert.showAndWait().ifPresent(response -> {
                if (response == newSession) {
                    // Just close the dialog and wait for the user to start again
                    subjectTextField.clear();
                    durationTextField.clear();
                    timerLabel.setText("00:00");
                    startButton.setText("Start Study Session");
                    isSessionActive = false;
                } else if (response == end) {
                    goToHomepage();
                }
            });
        });
    }

    /**
     * Go back to the login screen.
     */
    @FXML
    public void goToLogin() {
        try {
            Stage stage = (Stage) toLogin.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goToHomepage() {
        try {
            Stage stage = (Stage) toLogin.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("homepage.fxml")); // Make sure the path is correct
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

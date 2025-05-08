package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class StudyModeController {

    @FXML
    private Label timerLabel;  // Label for showing the timer

    @FXML
    private Button pauseButton;  // Button for pausing/resuming the study session

    @FXML
    private Label streakLabel;  // Label for showing the study streak

    @FXML
    private Label toLogin;  // Logout button

    private boolean isSessionActive = false;  // Track if the session is active
    private boolean isTimerStarted = false;  // Track if the timer has started
    private Timeline timer;  // Timeline for the study timer
    private int minutes = 0;  // Minutes counter for the timer
    private int seconds = 0;  // Seconds counter for the timer

    /**
     * Called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Set the initial streak value
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }

        // Set up the timer to update every second
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timer.setCycleCount(Timeline.INDEFINITE);

        // Initially set the button text to "Start Timer"
        pauseButton.setText("Start Timer");
    }

    /**
     * Start or pause the study session when the button is clicked.
     */
    @FXML
    private void pauseSession(MouseEvent event) {
        if (!isTimerStarted) {
            // If the timer hasn't started, start it
            timer.play();
            pauseButton.setText("Pause Study Session");
            isSessionActive = true;
            isTimerStarted = true;
        } else if (isSessionActive) {
            // If the session is active, pause it
            timer.stop();
            pauseButton.setText("Resume Study Session");
            isSessionActive = false;
        } else {
            // If the session is paused, resume it
            timer.play();
            pauseButton.setText("Pause Study Session");
            isSessionActive = true;
        }
    }

    /**
     * Update the timer on each tick.
     */
    private void updateTimer() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }

        // Format time as mm:ss
        String time = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(time);
    }

    /**
     * Go back to the login screen.
     */
    @FXML
    public void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Triggered when "Start Study Mode" is clicked on the homepage.
     */
    public void startStudyMode() {
        // Show a confirmation alert before starting study mode
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Start Study Mode?");
        alert.setHeaderText("Do you want to start the study mode?");
        alert.setContentText("Click Yes to start studying, or No to return to the homepage.");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.YES) {
                // Start study mode
                System.out.println("Study Mode started!");
            } else {
                // Go back to homepage (this will need to be implemented)
                System.out.println("Returning to homepage.");
            }
        });
    }
}

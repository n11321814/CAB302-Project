package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StudyModeController {

    @FXML
    private Label timerLabel;

    @FXML
    private Button startButton;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private ComboBox<String> moodComboBox;

    @FXML
    private Label streakLabel;

    @FXML
    private Label toLogin;

    @FXML
    private Label quoteLabel;  // Add a label for the quote

    private boolean isSessionActive = false;
    private Timeline timer;
    private int minutes = 0;
    private int seconds = 0;
    private int totalTimeInSeconds = 0;
    private boolean isPaused = false;
    private boolean sessionEnded = false;

    private Timeline quoteTimeline;  // Timeline for cycling quotes
    private List<String> quotes;  // List of quotes
    private int quoteIndex = 0;  // Current index of the quote list

    @FXML
    public void initialize() {
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }

        if (moodComboBox != null) {
            moodComboBox.getItems().addAll("Happy", "Stressed", "Tired", "Motivated", "Anxious");
        }

        // List of motivational quotes
        quotes = Arrays.asList(
                "The best way to predict the future is to create it.",
                "Success is the sum of small efforts, repeated day in and day out.",
                "Don’t watch the clock; do what it does. Keep going.",
                "It always seems impossible until it’s done.",
                "Believe in yourself and all that you are."
        );

        // Set the initial quote
        quoteLabel.setText(quotes.get(quoteIndex));

        // Setup the quote cycling timeline (every 15 seconds)
        quoteTimeline = new Timeline(new KeyFrame(Duration.seconds(15), e -> updateQuote()));
        quoteTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void startStudySession(MouseEvent event) {
        if (isSessionActive && !isPaused) {
            timer.pause();
            isPaused = true;
            startButton.setText("Resume Study Session");
            return;
        }

        if (isPaused) {
            timer.play();
            isPaused = false;
            startButton.setText("Pause Study Session");
            return;
        }

        // NEW: Get mood from ComboBox
        String mood = (moodComboBox != null) ? moodComboBox.getValue() : null;
        if (mood == null || mood.trim().isEmpty()) {
            showError("Please select your mood before starting the session.");
            return;
        }

        String subject = subjectTextField.getText();
        String durationText = durationTextField.getText();

        if (subject.isEmpty() || durationText.isEmpty()) {
            showError("Please enter both subject and duration.");
            return;
        }

        try {
            totalTimeInSeconds = Integer.parseInt(durationText) * 60;
            minutes = totalTimeInSeconds / 60;
            seconds = totalTimeInSeconds % 60;
            sessionEnded = false;

            updateTimerDisplay();

            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

            // Start the quote cycling timeline
            quoteTimeline.play();

            startButton.setText("Pause Study Session");
            isSessionActive = true;
            isPaused = false;

            // Optional: Log mood (could be saved later)
            System.out.println("Mood before session: " + mood);

        } catch (NumberFormatException e) {
            showError("Invalid duration format. Please enter a valid number.");
        }
    }

    /**
     * Update the quote every 15 seconds.
     */
    private void updateQuote() {
        // Cycle through the quotes
        quoteIndex = (quoteIndex + 1) % quotes.size();  // Loops back to the first quote
        quoteLabel.setText(quotes.get(quoteIndex));  // Update the label with the new quote
    }

    private void updateTimer() {
        if (totalTimeInSeconds > 0) {
            totalTimeInSeconds--;
            minutes = totalTimeInSeconds / 60;
            seconds = totalTimeInSeconds % 60;
            updateTimerDisplay();
        } else {
            if (!sessionEnded) {
                sessionEnded = true;
                timer.stop();
                showSessionEndPopup();
            }
        }
    }

    private void updateTimerDisplay() {
        String time = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(time);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
                    subjectTextField.clear();
                    durationTextField.clear();
                    moodComboBox.setValue(null); // Reset mood selection
                    timerLabel.setText("00:00");
                    startButton.setText("Start Study Session");
                    isSessionActive = false;
                } else if (response == end) {
                    goToHomepage();
                }
            });
        });
    }

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
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStudySessionToDatabase(String subject, int durationInSeconds) {
        int durationInMinutes = durationInSeconds / 60;
        System.out.println("Saving session to database...");
        System.out.println("Subject: " + subject);
        System.out.println("Duration: " + durationInMinutes + " minutes");
        // TODO: Replace this with actual DB logic
    }
}

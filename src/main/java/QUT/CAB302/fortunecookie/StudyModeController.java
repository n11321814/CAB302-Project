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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for the study mode view, managing the study timer, mood selection,
 * motivational quotes, and interactions with the AI assistant for personalized study advice.
 */
public class StudyModeController {

    // FXML UI elements for user interaction
    @FXML
    private Label timerLabel;  // Label for displaying the study session time
    @FXML
    private Button startButton;  // Button to start, pause, or resume the study session
    @FXML
    private TextField subjectTextField;  // Text field for entering the subject
    @FXML
    private TextField durationTextField;  // Text field for entering the study duration
    @FXML
    private ComboBox<String> moodComboBox;  // ComboBox for selecting mood
    @FXML
    private Label streakLabel;  // Label to display the study streak
    @FXML
    private Label toLogin;  // Label to navigate to the login page
    @FXML
    private Label quoteLabel;  // Label to display a motivational quote
    @FXML
    private Button saveQuoteButton;  // Button to save the current quote

    // State variables for study session
    private boolean isSessionActive = false;  // Tracks if the session is active
    private Timeline timer;  // Timeline for the study timer
    private int minutes = 0;  // Minutes counter for the timer
    private int seconds = 0;  // Seconds counter for the timer
    private int totalTimeInSeconds = 0;  // Total session time in seconds
    private boolean isPaused = false;  // Tracks if the session is paused
    private boolean sessionEnded = false;  // Tracks if the session has ended

    // State variables for quote cycling
    private Timeline quoteTimeline;  // Timeline for cycling quotes every 15 seconds
    private List<String> quotes;  // List of motivational quotes
    private int quoteIndex = 0;  // Current index of the quote list

    /**
     * Initializes the controller, setting up the mood options and quotes.
     * This method runs when the view is loaded.
     */
    @FXML
    public void initialize() {
        // Initialize streak label
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }

        // Initialize mood combo box with predefined options
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

    /**
     * Starts, pauses, or resumes the study session based on the current session state.
     * The method handles the start of a new session, toggles between pause and resume,
     * and updates the UI accordingly.
     *
     * @param event The MouseEvent triggered by the start button
     */
    @FXML
    private void startStudySession(MouseEvent event) {
        // Handle session pause and resume
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

        // Validate mood selection
        String mood = (moodComboBox != null) ? moodComboBox.getValue() : null;
        if (mood == null || mood.trim().isEmpty()) {
            showError("Please select your mood before starting the session.");
            return;
        }

        // Validate subject and duration input
        String subject = subjectTextField.getText();
        String durationText = durationTextField.getText();
        if (subject.isEmpty() || durationText.isEmpty()) {
            showError("Please enter both subject and duration.");
            return;
        }

        try {
            // Convert duration to seconds and initialize session
            totalTimeInSeconds = Integer.parseInt(durationText) * 60;
            minutes = totalTimeInSeconds / 60;
            seconds = totalTimeInSeconds % 60;
            sessionEnded = false;

            updateTimerDisplay();  // Update timer UI

            // Start the study timer
            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

            // Start the quote cycling
            quoteTimeline.play();

            startButton.setText("Pause Study Session");
            isSessionActive = true;
            isPaused = false;

            // Log the mood for future reference (could be saved in DB)
            System.out.println("Mood before session: " + mood);

        } catch (NumberFormatException e) {
            showError("Invalid duration format. Please enter a valid number.");
        }
    }

    /**
     * Updates the quote displayed every 15 seconds.
     */
    private void updateQuote() {
        // Cycle through the quotes list
        quoteIndex = (quoteIndex + 1) % quotes.size();
        quoteLabel.setText(quotes.get(quoteIndex));  // Update the label with the next quote
    }

    /**
     * Updates the study session timer, decrementing the time and updating the UI.
     * If the session time reaches 0, the session ends.
     */
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
                showSessionEndPopup();  // Show session end popup
            }
        }
    }

    /**
     * Updates the timer display label with the current time in MM:SS format.
     */
    private void updateTimerDisplay() {
        String time = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(time);
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message The error message to display
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation alert when the study session ends, offering the option
     * to start a new session or end the current one.
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
                    subjectTextField.clear();
                    durationTextField.clear();
                    moodComboBox.setValue(null);  // Reset mood selection
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
     * Navigates to the login screen.
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

    /**
     * Navigates to the homepage screen.
     */
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

    /**
     * Saves the study session details to the database.
     * (Placeholder method, implementation to be added.)
     *
     * @param subject The subject of the study session
     * @param durationInSeconds The duration of the session in seconds
     */
    private void saveStudySessionToDatabase(String subject, int durationInSeconds) {
        int durationInMinutes = durationInSeconds / 60;
        System.out.println("Saving session to database...");
        System.out.println("Subject: " + subject);
        System.out.println("Duration: " + durationInMinutes + " minutes");
        // TODO: Replace this with actual DB logic
    }

    /**
     * Saves the current motivational quote to the database.
     * (Placeholder method, for now just prints the quote to the console.)
     *
     * @param event The MouseEvent triggered by the save quote button
     */
    @FXML
    private void saveQuote(MouseEvent event) {
        // Get the current quote from the label
        String currentQuote = quoteLabel.getText();

        // Save the quote to database(for now, we'll print it to the console here)
        System.out.println("Quote saved: " + currentQuote);

        // Optionally, you could display a message saying the quote was saved
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quote Saved");
        alert.setHeaderText(null);
        alert.setContentText("The following quote has been saved:\n" + currentQuote);
        alert.showAndWait();
    }

    // AI Implementation

    @FXML
    private TextArea aiResponse;

    /**
     * Handles the interaction with the AI assistant to provide personalized study advice.
     * It sends a request to an AI model (using an HTTP POST request) and displays the response.
     */
    @FXML
    public void handleAskAI() {
        String subject = subjectTextField.getText();
        String duration = durationTextField.getText();
        String mood = moodComboBox.getValue();

        String model = "llama3.2:1b";
        String prompt = "I would like to study " + subject + " for " + duration + " minutes and I am in a " + mood + " mood. Given this context, what study advice can you give me?";
        Runnable task = () -> {
            try {
                // Set up an HTTP POST request
                URL url = new URL("http://localhost:11434/api/generate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create request JSON
                JSONObject requestJson = new JSONObject();
                requestJson.put("model", model);
                requestJson.put("prompt", prompt);
                requestJson.put("stream", false);

                // Send request
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(requestJson.toString().getBytes());
                }

                // Get response
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String responseLine = br.readLine();
                    JSONObject responseJson = new JSONObject(responseLine);
                    String fullResponse = responseJson.getString("response");

                    Platform.runLater(() -> aiResponse.setText(fullResponse));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> aiResponse.setText("Error: " + e.getMessage()));
            }
        };
        new Thread(task).start();
    }
}

package QUT.CAB302.fortunecookie.Controller;

import QUT.CAB302.fortunecookie.*;
import QUT.CAB302.fortunecookie.Model.BackButtonHandler;
import QUT.CAB302.fortunecookie.Model.BackNavigable;
import QUT.CAB302.fortunecookie.Model.SQLiteConnection;
import QUT.CAB302.fortunecookie.Model.UserSession;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class StudyModeController implements BackNavigable {

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
    private Label quoteLabel;

    @FXML
    private Button saveQuoteButton;

    @FXML
    private Button backToHome;

    @FXML
    private TextArea aiResponse;

    private boolean isSessionActive = false;
    private Timeline timer;
    private int minutes = 0;
    private int seconds = 0;
    private int totalTimeInSeconds = 0;
    private boolean isPaused = false;
    private boolean sessionEnded = false;

    private Timeline quoteTimeline;
    private List<String> quotes;
    private int quoteIndex = 0;

    @FXML
    public void initialize() {
        if (streakLabel != null) {
            streakLabel.setText("Study streak: 🔥 0");
        }

        if (moodComboBox != null) {
            moodComboBox.getItems().addAll("Happy", "Stressed", "Tired", "Motivated", "Anxious");
        }

        quotes = Arrays.asList(
                "The best way to predict the future is to create it.",
                "Success is the sum of small efforts, repeated day in and day out.",
                "Don’t watch the clock; do what it does. Keep going.",
                "It always seems impossible until it’s done.",
                "Believe in yourself and all that you are."
        );

        quoteLabel.setText(quotes.get(quoteIndex));

        quoteTimeline = new Timeline(new KeyFrame(Duration.seconds(15), e -> updateQuote()));
        quoteTimeline.setCycleCount(Timeline.INDEFINITE);

        BackButtonHandler.setBackAction(backToHome, this);
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

            // Log mood (please add it being saved later)
            System.out.println("Mood before session: " + mood);

        } catch (NumberFormatException e) {
            showError("Invalid duration format. Please enter a valid number.");
        }
    }

    /**
     * Update the quote every 15 seconds.
     */
    private void updateQuote() {
        quoteIndex = (quoteIndex + 1) % quotes.size();
        quoteLabel.setText(quotes.get(quoteIndex));
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
                    moodComboBox.setValue(null);
                    timerLabel.setText("00:00");
                    startButton.setText("Start Study Session");
                    isSessionActive = false;
                } else if (response == end) {
                    goToHomepage();
                }
            });
        });
    }

    public void goToHomepage() {
        try {
            Stage stage = (Stage) backToHome.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToLogin() {
        try {
            Stage stage = (Stage) timerLabel.getScene().getWindow(); // any node that exists on the scene
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Login.fxml"));
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

    @FXML
    private void saveQuote(MouseEvent event) {
        // Get the current quote from the label
        String currentQuote = quoteLabel.getText();
        int userId = UserSession.getUserId();

        String insertSql = "INSERT OR IGNORE INTO savedQuotes (id, savedQuote) VALUES (?, ?)";

        // Save the quote to database(for now, we'll print it to the console here)
        System.out.println("Quote saved: " + currentQuote);

        Connection connection = SQLiteConnection.getInstance();

        try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, userId);
            ps.setString(2,currentQuote);
            int rows = ps.executeUpdate();

            // Optionally, you could display a message saying the quote was saved
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quote Saved");
            alert.setHeaderText(null);
            if (rows > 0) {
                alert.setContentText("The following quote has been saved:\n" + currentQuote);
            } else {
                alert.setContentText("You have already saved this quote");
            }
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Failed to save quote:\n" + e.getMessage());
            alert.showAndWait();
        }
    }

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

    // Implement goBack from BackNavigable
    @Override
    public void goBack() {
        goToHomepage();
    }
}

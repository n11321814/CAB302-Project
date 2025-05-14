package QUT.CAB302.fortunecookie;

import javafx.application.Platform;
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

import org.json.JSONObject;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class HomepageController {


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

    @FXML
    public Button studyVaultButton;

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

    // AI Implementation

    @FXML
    private TextArea aiInput;
    @FXML
    private TextArea aiResponse;

    @FXML
    public void handleAskAI() {
        String model = "mistral";
        String prompt = aiInput.getText();
        String line;
        Runnable task = new Runnable() {
            @Override
            public void run() {

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
            }
        };
        new Thread(task).start();
    }
}

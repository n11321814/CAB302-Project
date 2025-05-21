package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Study Vault view, managing the display of saved quotes.
 * This allows users to view their saved motivational quotes from previous study sessions.
 */
public class StudyVaultController {

    // FXML UI elements for user interaction
    @FXML
    private Label backToHome;  // Label to navigate back to the homepage
    @FXML
    private ListView<String> quotesListView;  // ListView for displaying saved quotes

    /**
     * Initializes the study vault by loading saved quotes.
     * This method is called when the view is loaded.
     */
    @FXML
    public void initialize() {
        loadSavedQuotes();  // Load and display saved quotes when the view is initialized
    }

    /**
     * Loads saved quotes from the database for the specific user and displays them in the ListView.
     * If no quotes are saved, a placeholder message is displayed.
     */
    private void loadSavedQuotes() {
        // TODO: Replace with actual database access to retrieve saved quotes
        List<String> savedQuotes = List.of("Test1", "Test2", "Test3"); // Placeholder list

        // Clear any existing quotes in the ListView
        quotesListView.getItems().clear();

        // Add all saved quotes to the ListView
        quotesListView.getItems().addAll(savedQuotes);

        // If no quotes were saved, display a placeholder message
        if (savedQuotes.isEmpty()) {
            quotesListView.getItems().add("No saved quotes yet. Save some from your study sessions!");
        }
    }

    /**
     * Handles the back-to-homepage button functionality.
     * This method navigates the user back to the homepage view.
     */
    @FXML
    public void goToHomepage() {
        try {
            // Get the current stage and load the homepage FXML
            Stage stage = (Stage) backToHome.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);  // Set the new scene (homepage)
        } catch (IOException e) {
            e.printStackTrace();  // Log any errors during the navigation process
        }
    }
}

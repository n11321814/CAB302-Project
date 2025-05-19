package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
public class StudyVaultController {

    @FXML
    private Label backToHome;

    @FXML
    private ListView<String> quotesListView;

    /**
     * Initializes the study vault by loading saved quotes
     */
    @FXML
    public void initialize() {
        loadSavedQuotes();
    }

    /**
     * Loads saved quotes from specific user in database and stores then in a string list
      */
    private void loadSavedQuotes() {
        //
        List<String> savedQuotes = List.of("Test1", "Test2", "Test3"); // TODO access database and store in list

        // Clear existing items
        quotesListView.getItems().clear();

        // Add all saved quotes to the ListView
        quotesListView.getItems().addAll(savedQuotes);

        // If no quotes saved display a message
        if (savedQuotes.isEmpty()) {
            quotesListView.getItems().add("No saved quotes yet. Save some from your study sessions!");
        }
    }


    /**
     * Implementation of back to homepage button
     */
    @FXML
    public void goToHomepage() {
        try {
            Stage stage = (Stage) backToHome.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

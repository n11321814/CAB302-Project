package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        int userId = UserSession.getUserId();

        String query = "SELECT savedQuote FROM savedQuotes WHERE id = ?";
        List<String> savedQuotes = new ArrayList<>();

        Connection connection = SQLiteConnection.getInstance();

        try(PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                savedQuotes.add(rs.getString("savedQuote"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Clear existing items
        quotesListView.getItems().clear();
        // If no quotes saved display a message
        if (savedQuotes.isEmpty()) {
            quotesListView.getItems().add("No saved quotes yet. Save some from your study sessions!");
        } else {
            quotesListView.getItems().addAll(savedQuotes);
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

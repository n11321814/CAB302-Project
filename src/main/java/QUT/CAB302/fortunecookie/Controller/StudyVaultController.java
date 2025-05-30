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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Study Vault view.
 * <p>
 * Displays saved motivational quotes for the logged-in user.
 * Implements {@code BackNavigable} to support consistent back navigation.
 * </p>
 */
public class StudyVaultController implements BackNavigable {

    @FXML
    private Button toHome;  // Changed from Label to Button for better UX

    @FXML
    private ListView<String> quotesListView;

    /**
     * Initializes the Study Vault view.
     * <p>
     * Loads the user's saved quotes and sets the back button behavior.
     * </p>
     */
    @FXML
    public void initialize() {
        loadSavedQuotes();

        // Set back button action via BackButtonHandler (assumed helper)
        BackButtonHandler.setBackAction(toHome, this);
    }

    /**
     * Loads all saved quotes for the currently logged-in user from the database
     * and populates them into the {@code quotesListView}.
     * <p>
     * If no quotes are saved, a placeholder message is shown instead.
     * </p>
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
     * Navigates the user back to the homepage.
     * <p>
     * Triggered by the "Back to Home" button.
     * </p>
     */
    @FXML
    public void goToHomepage() {
        try {
            Stage stage = (Stage) toHome.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the back navigation action by redirecting the user to the homepage.
     */
    @Override
    public void goBack() {
        goToHomepage();
    }
}

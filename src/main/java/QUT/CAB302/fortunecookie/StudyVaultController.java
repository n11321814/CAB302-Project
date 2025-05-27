package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StudyVaultController implements BackNavigable {

    @FXML
    private Button backToHome;  // Changed from Label to Button for better UX

    @FXML
    private ListView<String> quotesListView;

    @FXML
    public void initialize() {
        loadSavedQuotes();

        // Set back button action via BackButtonHandler (assumed helper)
        BackButtonHandler.setBackAction(backToHome, this);
    }

    private void loadSavedQuotes() {
        List<String> savedQuotes = List.of("Test1", "Test2", "Test3"); // TODO: replace with DB fetch

        quotesListView.getItems().clear();
        if (savedQuotes.isEmpty()) {
            quotesListView.getItems().add("No saved quotes yet. Save some from your study sessions!");
        } else {
            quotesListView.getItems().addAll(savedQuotes);
        }
    }

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

    @Override
    public void goBack() {
        goToHomepage();
    }
}

package QUT.CAB302.fortunecookie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {
    @FXML
    private Button toLogin;

    @FXML
    public void goToLogin() throws IOException {
        Stage stage = (Stage) toLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationMain.WIDTH, ApplicationMain.HEIGHT);
        stage.setScene(scene);
    }
}

// This is the controller for homepage.fxml, for when homepage is finished.
package QUT.CAB302.fortunecookie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// Main class and entrypoint for the application
public class
ApplicationMain extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "Quote Me";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    // Loads the Login layout file, sets the scene height, width, and titled
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    // Main method, launches the application
    public static void main(String[] args) {
        launch();
    }
}
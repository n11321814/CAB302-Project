package QUT.CAB302.fortunecookie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The {@code ApplicationMain} class serves as the entry point for the JavaFX application.
 * It sets up the primary stage, loads the login screen, and displays the main window.
 */
public class ApplicationMain extends Application {

    /** The title of the application window. */
    public static final String TITLE = "Quote Me";

    /** The width of the application window in pixels. */
    public static final int WIDTH = 800;

    /** The height of the application window in pixels. */
    public static final int HEIGHT = 500;

    /**
     * Starts the JavaFX application.
     * Loads the login interface from an FXML file and displays it in the primary stage.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method. Launches the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
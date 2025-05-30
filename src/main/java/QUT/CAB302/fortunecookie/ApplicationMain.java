package QUT.CAB302.fortunecookie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main entry point for the Quote Me JavaFX application.
 * <p>
 * Initializes the primary stage with a login screen and sets default
 * dimensions to emulate a mobile phone layout.
 * </p>
 */
public class ApplicationMain extends Application {
    public static final String TITLE  = "Quote Me";
    public static final int    WIDTH  = 360;
    public static final int    HEIGHT = 640;

    /**
     * Starts the JavaFX application by setting up the primary stage.
     * <p>
     * Loads the initial login screen from FXML, sets up the scene with
     * fixed dimensions, and enforces a minimum window size.
     * </p>
     *
     * @param stage the primary application window
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();

        // Create scene at phone aspect ratio
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle(TITLE);
        stage.setScene(scene);

        // Enforce minimum size so it never shrinks below phone ratio
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);


        stage.show();
    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        launch();
    }
}


package QUT.CAB302.fortunecookie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// Main class and entrypoint for the application
public class ApplicationMain extends Application {
    // Phone-style default size
    public static final String TITLE  = "Quote Me";
    public static final int    WIDTH  = 360;
    public static final int    HEIGHT = 640;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("login.fxml"));
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

    public static void main(String[] args) {
        launch();
    }
}

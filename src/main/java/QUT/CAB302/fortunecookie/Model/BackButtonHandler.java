package QUT.CAB302.fortunecookie.Model;

import javafx.scene.control.Button;

public class BackButtonHandler {
    public static void setBackAction(Button backButton, BackNavigable controller) {
        backButton.setOnAction(event -> controller.goBack());
    }
}

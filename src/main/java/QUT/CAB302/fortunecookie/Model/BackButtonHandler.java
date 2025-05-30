package QUT.CAB302.fortunecookie.Model;

import javafx.scene.control.Button;

/**
 * Utility class for setting back navigation behavior on JavaFX buttons.
 * <p>
 * Designed to link a {@code Button} to a {@code BackNavigable} controller's {@code goBack()} method.
 * </p>
 */
public class BackButtonHandler {

    /**
     * Assigns a back action to the specified button that triggers the controller's {@code goBack()} method.
     *
     * @param backButton the button to attach the back action to
     * @param controller the controller implementing {@code BackNavigable}
     */
    public static void setBackAction(Button backButton, BackNavigable controller) {
        backButton.setOnAction(event -> controller.goBack());
    }
}

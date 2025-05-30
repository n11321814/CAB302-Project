package QUT.CAB302.fortunecookie.Model;

/**
 * Interface for controllers that support back navigation.
 * <p>
 * Implementing classes must define the {@code goBack()} method,
 * which is typically triggered by a back button or similar UI control.
 * </p>
 */
public interface BackNavigable {

    /**
     * Defines the action to take when navigating back from the current view.
     */
    void goBack();
}

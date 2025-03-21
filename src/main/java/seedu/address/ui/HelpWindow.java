package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    private static final String LIGHT_THEME_PATH = "/view/LightTheme.css";
    private static final String DARK_THEME_PATH = "/view/DarkTheme.css";
    private static final String HELP_WINDOW_LIGHT_THEME_PATH = "/view/HelpWindowLightTheme.css";
    private static final String HELP_WINDOW_DARK_THEME_PATH = "/view/HelpWindowDarkTheme.css";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    // Define default window size and position
    private static final double DEFAULT_WIDTH = 600;
    private static final double DEFAULT_HEIGHT = 600;
    private static final double WINDOW_OFFSET_X = 50;
    private static final double WINDOW_OFFSET_Y = 30;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        // Set initial size
        root.setWidth(DEFAULT_WIDTH);
        root.setHeight(DEFAULT_HEIGHT);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * If the user passed an owner stage, use it as the owner of the help window.
     * This helps ensure that the help window stays on top of the application.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");

        // Position the window at a fixed offset relative to the screen center
        // to avoid covering the main window completely
        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getMinX() + screenBounds.getWidth() / 2.0;
        double centerY = screenBounds.getMinY() + screenBounds.getHeight() / 2.0;

        // Position with a slight offset from center
        getRoot().setX(centerX - (DEFAULT_WIDTH / 2.0) + WINDOW_OFFSET_X);
        getRoot().setY(centerY - (DEFAULT_HEIGHT / 2.0) + WINDOW_OFFSET_Y);

        getRoot().show();
    }

    /**
     * Applies the current theme to the help window.
     * @param isDarkTheme true if the dark theme should be applied, false for light theme
     */
    public void applyTheme(boolean isDarkTheme) {
        Scene scene = getRoot().getScene();
        if (scene == null) {
            logger.warning("Cannot apply theme to help window - scene is null");
            return;
        }

        // Clear existing theme stylesheets
        scene.getStylesheets().removeIf(stylesheet ->
            stylesheet.contains("DarkTheme.css")
            || stylesheet.contains("LightTheme.css")
            || stylesheet.contains("HelpWindowDarkTheme.css")
            || stylesheet.contains("HelpWindowLightTheme.css"));

        // Apply the appropriate theme
        if (isDarkTheme) {
            scene.getStylesheets().add(0, HelpWindow.class.getResource(DARK_THEME_PATH).toExternalForm());
            scene.getStylesheets().add(HelpWindow.class.getResource(HELP_WINDOW_DARK_THEME_PATH).toExternalForm());
            logger.fine("Applied dark theme to help window");
        } else {
            scene.getStylesheets().add(0, HelpWindow.class.getResource(LIGHT_THEME_PATH).toExternalForm());
            scene.getStylesheets().add(HelpWindow.class.getResource(HELP_WINDOW_LIGHT_THEME_PATH).toExternalForm());
            logger.fine("Applied light theme to help window");
        }
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     * Also handles closing the help window when the Close button is clicked.
     */
    @FXML
    private void copyUrl() {
        // Check if the event came from the Close button
        Button sourceButton = (Button) copyButton.getScene().getFocusOwner();
        if (sourceButton != null && sourceButton != copyButton) {
            String buttonId = sourceButton.getText();

            // Close button handling
            if ("Close".equals(buttonId)) {
                hide();
                return;
            }
        }

        // Standard URL copy functionality
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}

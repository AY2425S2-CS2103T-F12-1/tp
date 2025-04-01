package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;

/**
 * Contains unit tests for {@code HelpWindow}.
 */
public class HelpWindowTest {
    private static final Logger logger = LogsCenter.getLogger(HelpWindowTest.class);
    private static final String EXPECTED_HELP_MESSAGE = "Refer to the user guide: "
            + HelpWindow.USERGUIDE_URL;

    @Test
    public void userGuideUrl_hasCorrectValue() {
        // Test userguide URL is correctly set
        assertEquals("https://se-education.org/addressbook-level3/UserGuide.html",
                HelpWindow.USERGUIDE_URL);
    }

    @Test
    public void helpMessage_hasCorrectValue() {
        // Test help message is correctly set
        assertEquals(EXPECTED_HELP_MESSAGE, HelpWindow.HELP_MESSAGE);
    }

    @Test
    public void userGuideUrl_isValidUrl() {
        // The URL should be available and start with https
        assertNotNull(HelpWindow.USERGUIDE_URL);
        assertTrue(HelpWindow.USERGUIDE_URL.startsWith("https://"));
    }
}


package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit tests for {@code HelpCommand}.
 */
public class HelpCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithModel_notAffectingModel() {
        // Execute help command
        new HelpCommand().execute(model);
        // Model should not be affected
        assertEquals(model, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand helpCommand = new HelpCommand();
        // same object -> returns true
        assertTrue(helpCommand.equals(helpCommand));
        // same values -> returns true
        HelpCommand helpCommandCopy = new HelpCommand();
        assertTrue(helpCommand.equals(helpCommandCopy));
        // different types -> returns false
        assertFalse(helpCommand.equals(1));
        // null -> returns false
        assertFalse(helpCommand.equals(null));
        // different command -> returns false (using ExitCommand for comparison)
        assertFalse(helpCommand.equals(new ExitCommand()));
    }

    @Test
    public void commandResult_showHelp_returnsTrue() {
        CommandResult commandResult = new HelpCommand().execute(model);
        assertTrue(commandResult.isShowHelp());
    }

    @Test
    public void commandResult_exit_returnsFalse() {
        CommandResult commandResult = new HelpCommand().execute(model);
        assertFalse(commandResult.isExit());
    }

    @Test
    public void commandResult_feedbackMessage_correct() {
        CommandResult commandResult = new HelpCommand().execute(model);
        assertEquals(SHOWING_HELP_MESSAGE, commandResult.getFeedbackToUser());
    }

    @Test
    public void toString_containsClassName() {
        HelpCommand command = new HelpCommand();
        String expected = HelpCommand.class.getSimpleName();
        // Test that the toString method returns a string containing the class name
        String actual = command.toString();
        assertTrue(actual.contains(expected),
                String.format("Expected toString to contain '%s', but was '%s'", expected, actual));
    }

    @Test
    public void execute_helpWithNullModel_success() {
        HelpCommand helpCommand = new HelpCommand();
        // The HelpCommand doesn't use the model, so it should work with null
        CommandResult result = helpCommand.execute(null);
        assertEquals(SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void execute_helpFollowedByExit_returnsDifferentResults() {
        // Help command should set showHelp to true and exit to false
        CommandResult helpResult = new HelpCommand().execute(model);
        assertTrue(helpResult.isShowHelp());
        assertFalse(helpResult.isExit());
        // Exit command should set showHelp to false and exit to true
        CommandResult exitResult = new ExitCommand().execute(model);
        assertFalse(exitResult.isShowHelp());
        assertTrue(exitResult.isExit());
        // The two results should be different
        assertFalse(helpResult.equals(exitResult));
    }
}


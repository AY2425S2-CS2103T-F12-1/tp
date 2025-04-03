package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;

/**
 * Adds an existing person as a participant (“member”) to an existing game.
 */
public class AddMemberCommand extends Command {

    public static final String COMMAND_WORD = "addmember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a member to the game identified by the index number used in the displayed game list.\n"
            + "Parameters: g/INDEX (must be a positive integer) n/PERSON_NAME\n"
            + "Example: " + COMMAND_WORD + " g/1 n/John Doe\n";

    public static final String MESSAGE_SUCCESS = "%1$s has been added to the game: %2$s";
    public static final String MESSAGE_PERSON_EXISTS =
            "%1$s is already a participant in this game. Each person can only be added once to a game.";
    public static final String MESSAGE_DUPLICATE_PERSONS =
            "Multiple people named '%1$s' were found. Please use a more specific \n"
            + "name or try using the 'list' command to see available contacts.";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "No person named '%1$s' was found in the address book. \n"
            + "Please add this person first using the 'add' command.";
    public static final String MESSAGE_INVALID_GAME_INDEX = "Game index %1$d is invalid. \n"
            + "Please use the 'list' command to see available games and their indices.";

    private final Index targetIndex;
    private final String memberName;

    /**
     * @param targetIndex Index of the game in the currently displayed game lis
     * @param memberName  The name of the Person to add as a participan
     */
    public AddMemberCommand(Index targetIndex, String memberName) {
        requireNonNull(targetIndex);
        requireNonNull(memberName);
        this.targetIndex = targetIndex;
        this.memberName = memberName;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Game> lastShownList = model.getFilteredGameList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_GAME_INDEX, targetIndex.getOneBased()));
        }

        Game gameToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (model.isPersonUnique(memberName) == -1) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSONS, memberName));
        } else if (model.isPersonUnique(memberName) == 0) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, memberName));
        }
        Person personToAdd = model.getPerson(memberName);
        if (gameToEdit.getParticipants().contains(personToAdd)) {
            throw new CommandException(String.format(
                    MESSAGE_PERSON_EXISTS, memberName, gameToEdit));
        }
        model.deleteGame(gameToEdit);
        gameToEdit.addParticipant(personToAdd);
        model.addGame(gameToEdit);

        return new CommandResult(String.format(
                MESSAGE_SUCCESS, personToAdd.getName().fullName, gameToEdit.getSport().sportName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddMemberCommand
                && targetIndex.equals(((AddMemberCommand) other).targetIndex)
                && memberName.equals(((AddMemberCommand) other).memberName));
    }
}

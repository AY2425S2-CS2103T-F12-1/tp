package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. Type 'help' to see available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided \n"
            + "is invalid. Please check that the index exists in the current person list.";
    public static final String MESSAGE_INVALID_GAME_DISPLAYED_INDEX = "The game index provided "
            + "is invalid. Please check that the index exists in the current game list.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "Found %1$d contact(s) matching your search criteria!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code game} for display to the user.
     */
    public static String format(Game game) {
        final StringBuilder builder = new StringBuilder();
        builder.append(game.getSport())
                .append(" at ")
                .append(game.getLocation().getAddress())
                .append(" on ")
                .append(game.getDateTime())
                .append(", Participants: ");
        if (game.getParticipants().size() == 0) {
            builder.append(0);
        } else {
            String participantNameList = game.getParticipants().stream().map(person -> person.getName().fullName)
                    .collect(Collectors.joining(", "));
            builder.append(participantNameList);
        }

        return builder.toString();
    }
}

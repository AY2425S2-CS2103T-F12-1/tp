package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Sport;

/**
 * Parses input arguments and creates a new DeleteSportCommand object.
 */
public class DeleteSportCommandParser implements Parser<DeleteSportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSportCommand
     * and returns a DeleteSportCommand object for execution.
     *
     * Expected format: INDEX s/SPORT_NAME
     *
     * @param userInput full user input string
     * @return a DeleteSportCommand object with the parsed index and sport
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteSportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
        }

        // Expect the first token to be the index and the remaining to include the sport prefix.
        String[] tokens = trimmedInput.split("\\s+", 2);
        if (tokens.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
        }

        // Parse the index (1-based index from user)
        Index index = ParserUtil.parseIndex(tokens[0]);

        // The remaining string should start with the sport prefix, e.g., "s/"
        String sportToken = tokens[1].trim();
        if (!sportToken.startsWith(CliSyntax.PREFIX_SPORT.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
        }
        String sportName = sportToken.substring(CliSyntax.PREFIX_SPORT.getPrefix().length()).trim();
        if (sportName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
        }

        Sport sport = new Sport(sportName);
        return new DeleteSportCommand(index, sport);
    }
}

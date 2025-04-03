package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Sport;

/**
 * Parses input arguments and creates a new AddSportCommand object.
 */
public class AddSportCommandParser implements Parser<AddSportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSportCommand
     * and returns an AddSportCommand object for execution.
     *
     * Expected format: INDEX s/SPORT_NAME
     *
     * @param userInput full user input string
     * @return an AddSportCommand object with the parsed index and spor
     * @throws ParseException if the user input does not conform the expected forma
     */
    @Override
    public AddSportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE));
        }


        String[] tokens = trimmedInput.split("\\s+", 2);
        if (tokens.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE));
        }


        Index index = ParserUtil.parseIndex(tokens[0]);


        String sportToken = tokens[1].trim();
        if (!sportToken.startsWith(CliSyntax.PREFIX_SPORT.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE));
        }
        String sportName = sportToken.substring(CliSyntax.PREFIX_SPORT.getPrefix().length()).trim();
        if (sportName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE));
        }


        Sport sport = new Sport(sportName.toLowerCase());
        return new AddSportCommand(index.getZeroBased(), sport);
    }
}

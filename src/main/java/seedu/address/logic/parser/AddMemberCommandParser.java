package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddMemberCommand object.
 */
public class AddMemberCommandParser implements Parser<AddMemberCommand> {

    @Override
    public AddMemberCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GAME_NAME, PREFIX_NAME);

        if (!argMultimap.getValue(PREFIX_GAME_NAME).isPresent()
                || !argMultimap.getValue(PREFIX_NAME).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMemberCommand.MESSAGE_USAGE));
        }


        String gameIndexStr = argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).get();
        Index gameIndex;

        gameIndex = ParserUtil.parseIndex(gameIndexStr);
        String personName = argMultimap.getValue(CliSyntax.PREFIX_NAME).get().trim();

        return new AddMemberCommand(gameIndex, personName);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.testutil.TypicalPersons;

public class AddMemberCommandParserTest {
    private AddMemberCommandParser parser = new AddMemberCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        AddMemberCommand expectedCommand =
                new AddMemberCommand(INDEX_FIRST_OBJECT, TypicalPersons.BOB.getName().fullName);
        assertParseSuccess(parser, " g/1 n/Bob Choo", expectedCommand);

        assertParseSuccess(parser, " g/1   n/Bob Choo  ", expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE);


        assertParseFailure(parser, "n/Bob", expectedMessage);


        assertParseFailure(parser, "g/1", expectedMessage);


        assertParseFailure(parser, "1 Bob", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " g/0 n/Bob Choo",
                MESSAGE_INVALID_INDEX);


        assertParseFailure(parser, "g/1 n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));


        assertParseFailure(parser, "g/1n/bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
    }
}

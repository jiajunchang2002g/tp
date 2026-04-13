package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {

    private final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_emptyArgs_returnsClearCommandWithoutToken() {
        assertParseSuccess(parser, "   ", new ClearCommand());
    }

    @Test
    public void parse_singleToken_returnsClearCommandWithToken() {
        assertParseSuccess(parser, "ABC123", new ClearCommand("ABC123"));
        assertParseSuccess(parser, "   2X7Q9P   ", new ClearCommand("2X7Q9P"));
    }

    @Test
    public void parse_multipleTokens_throwsParseException() {
        assertParseFailure(parser, "ABC DEF",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameAndTagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
                        Collections.emptySet()));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_tagOnlyArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndTagContainsKeywordsPredicate(Collections.emptyList(),
                        Set.of(new Tag("friends"))));
        assertParseSuccess(parser, " t/friends", expectedFindCommand);
    }

    @Test
    public void parse_nameAndTagArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndTagContainsKeywordsPredicate(Arrays.asList("Alice"),
                        Set.of(new Tag("friends"))));
        assertParseSuccess(parser, " Alice t/friends", expectedFindCommand);
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        assertParseFailure(parser, " Alice t/friend*", Tag.MESSAGE_CONSTRAINTS);
    }

}

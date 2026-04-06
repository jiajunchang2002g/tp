package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameAndTagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String normalizedArgs = args.startsWith(" ") ? args : " " + args;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(normalizedArgs, PREFIX_TAG);
        List<String> nameKeywords = extractNameKeywords(argMultimap.getPreamble());
        Set<Tag> tagKeywords = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new NameAndTagContainsKeywordsPredicate(nameKeywords, tagKeywords));
    }

    private List<String> extractNameKeywords(String preamble) {
        String trimmedPreamble = preamble.trim();
        if (trimmedPreamble.isEmpty()) {
            return List.of();
        }

        return Arrays.stream(trimmedPreamble.split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
    }

}

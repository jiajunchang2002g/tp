package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Alias;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Risk;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a raw {@code al/} string into a list of {@code Alias}.
     * Values are comma-separated. Leading/trailing whitespaces around each alias will be trimmed.
     *
     * <p>An empty input returns an empty alias list.</p>
     *
     * @throws ParseException if any alias token is invalid.
     */
    public static List<Alias> parseAliases(String rawAliases) throws ParseException {
        requireNonNull(rawAliases);
        String trimmed = rawAliases.trim();
        if (trimmed.isEmpty()) {
            return List.of();
        }

        List<String> tokens = List.of(trimmed.split(","));
        try {
            return tokens.stream()
                    .map(String::trim)
                    .map(token -> {
                        if (token.isEmpty() || !Alias.isValidAlias(token)) {
                            throw new IllegalArgumentException("Invalid alias token");
                        }
                        return new Alias(token);
                    })
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new ParseException(Alias.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String notes} into {@code Notes}.
     *
     * @throws ParseException if the given {@code notes} is invalid.
     */
    public static Notes parseNotes(String notes) throws ParseException {
        requireNonNull(notes);
        if (!Notes.isValidNotes(notes)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS);
        }
        return new Notes(notes);
    }

    /**
     * Parses a {@code String risk} into a {@code Risk}.
     *
     * @throws ParseException if the given {@code risk} is invalid.
     */
    public static Risk parseRisk(String risk) throws ParseException {
        requireNonNull(risk);
        try {
            return Risk.fromString(risk);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(Risk.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}

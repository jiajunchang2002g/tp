package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Encounter with a Person in the address book.
 * Guarantees: immutable; fields are present and not null; description is valid as declared in
 * {@link #isValidDescription(String)}.
 */
public class Encounter {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Encounter descriptions can take any values, and should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";

    public final LocalDateTime dateTime;
    public final String description;

    /**
     * Constructs an {@code Encounter}.
     *
     * @param dateTime    The date and time of the encounter; must not be null.
     * @param description A valid description of the encounter.
     */
    public Encounter(LocalDateTime dateTime, String description) {
        requireNonNull(dateTime);
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.dateTime = dateTime;
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid encounter description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    /**
     * Returns the date and time of the encounter formatted as {@value DATE_TIME_FORMAT}.
     */
    public String getFormattedDateTime() {
        return dateTime.format(FORMATTER);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("dateTime", getFormattedDateTime())
                .add("description", description)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Encounter)) {
            return false;
        }

        Encounter otherEncounter = (Encounter) other;
        return dateTime.equals(otherEncounter.dateTime)
                && description.equals(otherEncounter.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description);
    }

}

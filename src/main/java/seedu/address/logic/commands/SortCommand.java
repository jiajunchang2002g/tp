package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Encounter;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Sorts the contact list by a supported criterion.
 *
 * <p>Note: in this step, this command only prepares criterion/comparator logic.
 * Parser and model integration are implemented in later steps.</p>
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed list of contacts by a criterion.\n"
            + "Parameters: CRITERION (location|tag|alphabetical|status|recent)\n"
            + "Example: " + COMMAND_WORD + " location";

    public static final String MESSAGE_SUCCESS = "Sort criterion selected: %1$s";

    private final SortCriterion criterion;

    /**
     * Creates a {@code SortCommand} with the given criterion.
     */
    public SortCommand(SortCriterion criterion) {
        requireNonNull(criterion);
        this.criterion = criterion;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, criterion.getToken()));
    }

    /**
     * Returns the comparator for this command's criterion.
     */
    public Comparator<Person> getComparator() {
        return criterion.getComparator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SortCommand)) {
            return false;
        }
        SortCommand otherSortCommand = (SortCommand) other;
        return criterion == otherSortCommand.criterion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("criterion", criterion)
                .toString();
    }

    /**
     * Supported sort criteria.
     */
    public enum SortCriterion {
        LOCATION("location", buildLocationComparator()),
        TAG("tag", buildTagComparator()),
        ALPHABETICAL("alphabetical", buildAlphabeticalComparator()),
        STATUS("status", buildStatusComparator()),
        RECENT("recent", buildRecentComparator());

        private final String token;
        private final Comparator<Person> comparator;

        SortCriterion(String token, Comparator<Person> comparator) {
            this.token = token;
            this.comparator = comparator;
        }

        public String getToken() {
            return token;
        }

        public Comparator<Person> getComparator() {
            return comparator;
        }

        /**
         * Parses a raw string into a sort criterion.
         */
        public static Optional<SortCriterion> fromString(String rawValue) {
            if (rawValue == null) {
                return Optional.empty();
            }
            String normalized = rawValue.trim().toLowerCase(Locale.ROOT);
            for (SortCriterion criterion : values()) {
                if (criterion.token.equals(normalized)) {
                    return Optional.of(criterion);
                }
            }
            return Optional.empty();
        }

        private static Comparator<Person> buildAlphabeticalComparator() {
            return Comparator.comparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        }

        private static Comparator<Person> buildStatusComparator() {
            return Comparator.comparing((Person person) -> person.getStage().toString(), String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        }

        private static Comparator<Person> buildLocationComparator() {
            return Comparator.comparing(
                            SortCriterion::getLatestEncounterLocation,
                            Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                    .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        }

        private static Comparator<Person> buildTagComparator() {
            return Comparator.comparing(
                            SortCriterion::getSmallestTagName,
                            Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                    .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        }

        private static Comparator<Person> buildRecentComparator() {
            return Comparator.comparing(
                            SortCriterion::getLatestEncounterTime,
                            Comparator.nullsLast(Comparator.reverseOrder()))
                    .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        }

        private static String getLatestEncounterLocation(Person person) {
            return person.getEncounters().isEmpty()
                    ? null
                    : person.getEncounters().get(person.getEncounters().size() - 1).location;
        }

        private static String getSmallestTagName(Person person) {
            return person.getTags().stream()
                    .map(tag -> tag.tagName)
                    .min(String.CASE_INSENSITIVE_ORDER)
                    .orElse(null);
        }

        private static LocalDateTime getLatestEncounterTime(Person person) {
            return person.getEncounters().isEmpty()
                    ? null
                    : person.getEncounters().get(person.getEncounters().size() - 1).dateTime;
        }
    }
}

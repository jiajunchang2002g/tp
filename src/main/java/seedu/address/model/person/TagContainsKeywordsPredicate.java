package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s tags match any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Creates a predicate that checks whether a person's tags match any of the given keywords.
     *
     * @param keywords Keywords to match against a person's tags.
     */
    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns true if the given {@code person} has at least one tag matching any keyword.
     *
     * @param person Person to test against the configured tag keywords.
     * @return True if any tag matches any keyword, ignoring case.
     */
    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

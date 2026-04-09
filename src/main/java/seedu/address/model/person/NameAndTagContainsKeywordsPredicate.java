package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} or aliases match any of the name keywords
 * and that the person's tags match any of the tag keywords.
 */
public class NameAndTagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final Set<Tag> tagKeywords;

    /**
     * Creates a predicate that filters persons by the given name and tag keywords.
     * Empty name keywords or tag keywords mean that category is not used for filtering.
     *
     * @param nameKeywords Keywords to match against person names.
     * @param tagKeywords Tags to match against person tags.
     */
    public NameAndTagContainsKeywordsPredicate(List<String> nameKeywords, Set<Tag> tagKeywords) {
        requireNonNull(nameKeywords);
        requireNonNull(tagKeywords);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.tagKeywords = Set.copyOf(tagKeywords);
    }

    /**
     * Returns true if the given {@code person} matches the configured name and tag filters.
     *
     * @param person Person to test against the configured filters.
     * @return True if the person satisfies both active filters.
     */
    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            return false;
        }
        return matchesName(person) && matchesTag(person);
    }

    private boolean matchesName(Person person) {
        return nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || person.getAliases().stream()
                        .anyMatch(alias -> StringUtil.containsWordIgnoreCase(alias.value, keyword)));
    }

    private boolean matchesTag(Person person) {
        return tagKeywords.isEmpty() || person.getTags().stream()
                .anyMatch(personTag -> tagKeywords.stream()
                        .anyMatch(tagKeyword -> personTag.tagName.equalsIgnoreCase(tagKeyword.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameAndTagContainsKeywordsPredicate)) {
            return false;
        }

        NameAndTagContainsKeywordsPredicate otherPredicate = (NameAndTagContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && tagKeywords.equals(otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("tagKeywords", tagKeywords)
                .toString();
    }
}

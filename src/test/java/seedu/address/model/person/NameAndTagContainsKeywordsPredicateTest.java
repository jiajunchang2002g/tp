package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class NameAndTagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        NameAndTagContainsKeywordsPredicate first = new NameAndTagContainsKeywordsPredicate(
                Collections.singletonList("first"), Set.of(new Tag("a")));
        NameAndTagContainsKeywordsPredicate second = new NameAndTagContainsKeywordsPredicate(
                Arrays.asList("first", "second"), Set.of(new Tag("a")));

        assertTrue(first.equals(first));
        assertTrue(first.equals(new NameAndTagContainsKeywordsPredicate(
                Collections.singletonList("first"), Set.of(new Tag("a")))));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
        assertFalse(first.equals(second));
    }

    @Test
    public void test_bothEmpty_returnsFalse() {
        NameAndTagContainsKeywordsPredicate predicate = new NameAndTagContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_nameOnly_matchesName() {
        NameAndTagContainsKeywordsPredicate predicate = new NameAndTagContainsKeywordsPredicate(
                Collections.singletonList("Alice"), Collections.emptySet());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").withAliases("Alice Fox").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void test_tagOnly_matchesTag() {
        NameAndTagContainsKeywordsPredicate predicate = new NameAndTagContainsKeywordsPredicate(
                Collections.emptyList(), Set.of(new Tag("friend")));
        assertTrue(predicate.test(new PersonBuilder().withName("X").withTags("friend").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("X").withTags("foe").build()));
    }

    @Test
    public void test_nameAndTag_requiresBoth() {
        NameAndTagContainsKeywordsPredicate predicate = new NameAndTagContainsKeywordsPredicate(
                Collections.singletonList("Amy"), Set.of(new Tag("vip")));
        assertTrue(predicate.test(new PersonBuilder().withName("Amy Bee").withTags("vip").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Amy Bee").withTags("regular").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("vip").build()));
    }

    @Test
    public void test_tagMatchIsCaseInsensitive() {
        NameAndTagContainsKeywordsPredicate predicate = new NameAndTagContainsKeywordsPredicate(
                Collections.emptyList(), Set.of(new Tag("wanted")));
        assertTrue(predicate.test(new PersonBuilder().withTags("WANTED").build()));
    }

    @Test
    public void toStringMethod() {
        NameAndTagContainsKeywordsPredicate predicate = new NameAndTagContainsKeywordsPredicate(
                Arrays.asList("keyword1", "keyword2"), Set.of(new Tag("t")));
        String s = predicate.toString();
        assertTrue(s.contains("nameKeywords"));
        assertTrue(s.contains("tagKeywords"));
        assertTrue(s.contains("keyword1"));
    }
}

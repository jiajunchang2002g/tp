package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class NameAndTagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstNameKeywordList = Collections.singletonList("first");
        List<String> secondNameKeywordList = Arrays.asList("first", "second");
        Set<Tag> firstTagKeywordSet = Set.of(new Tag("friends"));
        Set<Tag> secondTagKeywordSet = Set.of(new Tag("owesMoney"));

        NameAndTagContainsKeywordsPredicate firstPredicate =
                new NameAndTagContainsKeywordsPredicate(firstNameKeywordList, firstTagKeywordSet);
        NameAndTagContainsKeywordsPredicate secondPredicate =
                new NameAndTagContainsKeywordsPredicate(secondNameKeywordList, secondTagKeywordSet);

        assertTrue(firstPredicate.equals(firstPredicate));

        NameAndTagContainsKeywordsPredicate firstPredicateCopy =
                new NameAndTagContainsKeywordsPredicate(firstNameKeywordList, firstTagKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameOrTagContainsKeywords_returnsTrue() {
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Alice"), Collections.emptySet());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new NameAndTagContainsKeywordsPredicate(Collections.emptyList(), Set.of(new Tag("friends")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));

        predicate = new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Alice"),
                Set.of(new Tag("friends")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));

        predicate = new NameAndTagContainsKeywordsPredicate(Collections.emptyList(), Set.of(new Tag("FrIeNdS")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_nameOrTagDoesNotContainKeywords_returnsFalse() {
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Carol"), Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new NameAndTagContainsKeywordsPredicate(Collections.emptyList(), Set.of(new Tag("friends")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("colleagues").build()));

        predicate = new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Alice"),
                Set.of(new Tag("friends")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("colleagues").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("keyword1", "keyword2");
        Set<Tag> tagKeywords = Set.of(new Tag("friends"));
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(nameKeywords, tagKeywords);

        String expected = NameAndTagContainsKeywordsPredicate.class.getCanonicalName()
                + "{nameKeywords=" + nameKeywords + ", tagKeywords=" + tagKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}


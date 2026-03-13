package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EncounterTest {

    private static final LocalDateTime VALID_DATE_TIME = LocalDateTime.of(2024, 1, 15, 14, 30);
    private static final String VALID_DESCRIPTION = "Discussed project details over coffee";

    @Test
    public void constructor_nullDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Encounter(null, VALID_DESCRIPTION));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Encounter(VALID_DATE_TIME, null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Encounter(VALID_DATE_TIME, "")); // empty
        assertThrows(IllegalArgumentException.class, () -> new Encounter(VALID_DATE_TIME, " ")); // spaces only
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Encounter.isValidDescription(null));

        // invalid descriptions
        assertFalse(Encounter.isValidDescription("")); // empty string
        assertFalse(Encounter.isValidDescription(" ")); // spaces only
        assertFalse(Encounter.isValidDescription("   ")); // multiple spaces

        // valid descriptions
        assertTrue(Encounter.isValidDescription("Had lunch")); // short description
        assertTrue(Encounter.isValidDescription("-")); // single non-whitespace character
        assertTrue(Encounter.isValidDescription("1")); // single digit
        assertTrue(Encounter.isValidDescription("Met at conference and discussed new project ideas")); // long
        assertTrue(Encounter.isValidDescription("Catch-up; discussed Q4 plans & budget.")); // special characters
        assertTrue(Encounter.isValidDescription("Hello world")); // leading non-whitespace, space inside
    }

    @Test
    public void getFormattedDateTime() {
        Encounter encounter = new Encounter(VALID_DATE_TIME, VALID_DESCRIPTION);
        assertEquals("2024-01-15 14:30", encounter.getFormattedDateTime());
    }

    @Test
    public void getFormattedDateTime_paddedValues() {
        // month, day, hour, minute all single-digit — should be zero-padded
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 5, 9, 7);
        Encounter encounter = new Encounter(dateTime, VALID_DESCRIPTION);
        assertEquals("2024-03-05 09:07", encounter.getFormattedDateTime());
    }

    @Test
    public void equals() {
        Encounter encounter = new Encounter(VALID_DATE_TIME, VALID_DESCRIPTION);

        // same values -> returns true
        assertTrue(encounter.equals(new Encounter(VALID_DATE_TIME, VALID_DESCRIPTION)));

        // same object -> returns true
        assertTrue(encounter.equals(encounter));

        // null -> returns false
        assertFalse(encounter.equals(null));

        // different type -> returns false
        assertFalse(encounter.equals(5));

        // different dateTime -> returns false
        assertFalse(encounter.equals(new Encounter(VALID_DATE_TIME.plusHours(1), VALID_DESCRIPTION)));
        assertFalse(encounter.equals(new Encounter(VALID_DATE_TIME.plusDays(1), VALID_DESCRIPTION)));

        // different description -> returns false
        assertFalse(encounter.equals(new Encounter(VALID_DATE_TIME, "Different description")));

        // both fields different -> returns false
        assertFalse(encounter.equals(new Encounter(VALID_DATE_TIME.plusHours(1), "Different description")));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        Encounter encounter1 = new Encounter(VALID_DATE_TIME, VALID_DESCRIPTION);
        Encounter encounter2 = new Encounter(VALID_DATE_TIME, VALID_DESCRIPTION);
        assertEquals(encounter1.hashCode(), encounter2.hashCode());
    }

    @Test
    public void toStringMethod() {
        Encounter encounter = new Encounter(VALID_DATE_TIME, VALID_DESCRIPTION);
        String expected = Encounter.class.getCanonicalName()
                + "{dateTime=" + encounter.getFormattedDateTime()
                + ", description=" + VALID_DESCRIPTION + "}";
        assertEquals(expected, encounter.toString());
    }

}

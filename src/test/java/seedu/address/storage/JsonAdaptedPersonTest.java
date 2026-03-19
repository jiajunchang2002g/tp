package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Risk;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_NOTES = "line1\nline2";
    private static final String INVALID_RISK = "urgent";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_STAGE = BENSON.getStage().toString();
    private static final List<String> VALID_ALIASES = List.of("Ah Boy", "Johnny T");
    private static final String VALID_NOTES = "Met at cafe.";
    private static final String VALID_RISK = Risk.getDefault().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME,
                        VALID_ADDRESS, VALID_STAGE, VALID_ALIASES, VALID_NOTES, VALID_RISK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null,
                        VALID_ADDRESS, VALID_STAGE, VALID_ALIASES, VALID_NOTES, VALID_RISK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME,
                        INVALID_ADDRESS, VALID_STAGE, VALID_ALIASES, VALID_NOTES, VALID_RISK);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME,
                        null, VALID_STAGE, VALID_ALIASES, VALID_NOTES, VALID_RISK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNotes_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME,
                        VALID_ADDRESS, VALID_STAGE, VALID_ALIASES, INVALID_NOTES, VALID_RISK);
        String expectedMessage = Notes.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRisk_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME,
                        VALID_ADDRESS, VALID_STAGE, VALID_ALIASES, VALID_NOTES, INVALID_RISK);
        String expectedMessage = Risk.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RISK_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.RISK_DESC_LOW;
import static seedu.address.logic.commands.CommandTestUtil.STAGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STAGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Risk;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withRisk("high").build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + ADDRESS_DESC_BOB + STAGE_DESC_BOB + RISK_DESC_HIGH, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB
                + ADDRESS_DESC_BOB + STAGE_DESC_BOB + RISK_DESC_HIGH;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple stages
        assertParseFailure(parser, STAGE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STAGE));

        // multiple risks
        assertParseFailure(parser, RISK_DESC_LOW + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RISK));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_STAGE, PREFIX_RISK));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // risk omitted -> defaults to medium
        Person expectedPerson = new PersonBuilder(AMY).withRisk(Risk.getDefault().toString()).build();
        assertParseSuccess(parser, NAME_DESC_AMY
                + ADDRESS_DESC_AMY + STAGE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ADDRESS_DESC_BOB
                + STAGE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ADDRESS_BOB
                + STAGE_DESC_BOB, expectedMessage);

        // missing stage prefix
        assertParseFailure(parser, NAME_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ADDRESS_DESC_BOB
                + STAGE_DESC_BOB + RISK_DESC_HIGH, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ADDRESS_DESC
                + STAGE_DESC_BOB + RISK_DESC_HIGH, Address.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC
                + INVALID_ADDRESS_DESC + STAGE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                + ADDRESS_DESC_BOB + STAGE_DESC_BOB + RISK_DESC_HIGH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

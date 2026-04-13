package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private static final Pattern CHALLENGE_PATTERN = Pattern.compile("clear\\s+([A-Z0-9]+)");

    @BeforeEach
    public void resetChallenge() {
        ClearCommand.resetPendingChallengeForTesting();
    }

    @Test
    public void execute_withoutToken_generatesChallengeAndDoesNotClear() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        CommandResult result = new ClearCommand().execute(model);

        assertTrue(result.getFeedbackToUser().contains("Safety check:"));
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_withCorrectToken_clearsAddressBook() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult challengeResult = new ClearCommand().execute(model);
        String challengeToken = extractChallengeToken(challengeResult.getFeedbackToUser());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        assertCommandSuccess(new ClearCommand(challengeToken), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withWrongToken_doesNotClearAndRefreshesChallenge() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult firstChallengeResult = new ClearCommand().execute(model);
        String firstToken = extractChallengeToken(firstChallengeResult.getFeedbackToUser());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult wrongTokenResult = new ClearCommand("WRONG1").execute(model);
        String refreshedToken = extractChallengeToken(wrongTokenResult.getFeedbackToUser());

        assertTrue(wrongTokenResult.getFeedbackToUser().contains("Incorrect challenge token."));
        assertEquals(expectedModel, model);
        assertNotEquals(firstToken, refreshedToken);
    }

    private String extractChallengeToken(String feedback) {
        Matcher matcher = CHALLENGE_PATTERN.matcher(feedback);
        assertTrue(matcher.find());
        return matcher.group(1);
    }

}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.security.SecureRandom;
import java.util.Objects;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears CrimeWatch.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all data after a challenge confirmation.\n"
            + "Usage: " + COMMAND_WORD + " (to generate challenge), then " + COMMAND_WORD + " <challenge>";
    public static final String MESSAGE_SUCCESS = "CrimeWatch has been cleared!";
    public static final String MESSAGE_CHALLENGE = "Safety check: run '%s %s' to confirm clearing all data.";
    public static final String MESSAGE_NO_PENDING_CHALLENGE = "No pending clear challenge. Run 'clear' first.";
    public static final String MESSAGE_INCORRECT_CHALLENGE =
            "Incorrect challenge token. New challenge: run '%s %s' to confirm.";

    private static final String CHALLENGE_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CHALLENGE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String pendingChallenge;

    private final String confirmationToken;

    public ClearCommand() {
        this(null);
    }

    public ClearCommand(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (confirmationToken == null) {
            pendingChallenge = generateChallenge();
            return new CommandResult(String.format(MESSAGE_CHALLENGE, COMMAND_WORD, pendingChallenge));
        }

        if (pendingChallenge == null) {
            return new CommandResult(MESSAGE_NO_PENDING_CHALLENGE);
        }

        if (!pendingChallenge.equals(confirmationToken)) {
            pendingChallenge = generateChallenge();
            return new CommandResult(String.format(MESSAGE_INCORRECT_CHALLENGE, COMMAND_WORD, pendingChallenge));
        }

        model.setAddressBook(new AddressBook());
        pendingChallenge = null;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClearCommand)) {
            return false;
        }
        ClearCommand otherClearCommand = (ClearCommand) other;
        return Objects.equals(confirmationToken, otherClearCommand.confirmationToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmationToken);
    }

    static void resetPendingChallengeForTesting() {
        pendingChallenge = null;
    }

    private static String generateChallenge() {
        StringBuilder builder = new StringBuilder(CHALLENGE_LENGTH);
        for (int i = 0; i < CHALLENGE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHALLENGE_CHARACTERS.length());
            builder.append(CHALLENGE_CHARACTERS.charAt(randomIndex));
        }
        return builder.toString();
    }
}

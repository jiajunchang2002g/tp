package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;

/**
 * Adds a reminder to a contact identified by index in the displayed contact list.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a reminder for the contact identified by the index number "
            + "used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_NOTES + "NOTE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2026-03-28 "
            + PREFIX_TIME + "20:00 "
            + PREFIX_NOTES + "Meet informant";

    public static final String MESSAGE_SUCCESS = "Reminder set for %1$s on %2$s %3$s.";

    private final Index index;
    private final Reminder reminder;

    /**
     * @param index index of person in the filtered person list to set the reminder for
     * @param reminder reminder to add to the person's reminders list
     */
    public RemindCommand(Index index, Reminder reminder) {
        requireNonNull(index);
        requireNonNull(reminder);
        this.index = index;
        this.reminder = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(index.getZeroBased());
        model.addReminderToContact(index, reminder);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                targetPerson.getName(),
                reminder.getDate(),
                reminder.getTime()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemindCommand)) {
            return false;
        }

        RemindCommand otherCommand = (RemindCommand) other;
        return index.equals(otherCommand.index)
                && reminder.equals(otherCommand.reminder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("reminder", reminder)
                .toString();
    }
}

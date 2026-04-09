package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in CrimeWatch to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        showAllPersons(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Restores the list view to show all persons.
     */
    private void showAllPersons(Model model) {
        model.clearPersonSortComparator();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
}

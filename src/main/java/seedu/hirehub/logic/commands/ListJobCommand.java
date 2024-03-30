package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.model.Model.PREDICATE_SHOW_ALL_JOBS;

import seedu.hirehub.model.Model;

/**
 * Lists all jobs in the address book to the user.
 */
public class ListJobCommand extends Command {

    public static final String COMMAND_WORD = "list_job";

    public static final String MESSAGE_SUCCESS = "Listed all available jobs from the list!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

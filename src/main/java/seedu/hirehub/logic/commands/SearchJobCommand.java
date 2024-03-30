package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_VACANCY;

import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.SearchPredicate;

/**
 *
 */
public class SearchJobCommand extends Command {

    public static final String COMMAND_WORD = "search_job";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all jobs whose attributes match all "
            + "the corresponding specified attributes.\n"
            + "Parameters: [" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_VACANCY + "VACANCY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "ML "
            + PREFIX_DESCRIPTION + "C++ "
            + PREFIX_VACANCY + "10";

    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one field to search for must be provided.";

    private final SearchPredicate<Job> searchPredicate;

    public SearchJobCommand(SearchPredicate<Job> searchPredicate) {
        this.searchPredicate = searchPredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredJobList(searchPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW,
                        model.getFilteredJobList().size())
        );
    }
}

package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.person.SearchPredicate;

/**
 * Searches all applications whose attributes match the specified attributes.
 */
public class SearchApplicationCommand extends Command {

    public static final String COMMAND_WORD = "search_app";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all applications whose attributes match all "
            + "the corresponding specified attributes.\n"
            + "Parameters: [" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_TITLE + "Software Engineer "
            + PREFIX_STATUS + "IN_PROGRESS";

    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one field to search for must be provided.";

    private final SearchPredicate<Application> searchPredicate;

    public SearchApplicationCommand(SearchPredicate<Application> searchPredicate) {
        this.searchPredicate = searchPredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredApplicationList(searchPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        model.getFilteredApplicationList().size())
        );
    }
}

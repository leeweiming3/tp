package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.application.Application;

/**
 * Pushes the program into the state to delete the application at targetIndex.
 */
public class NewDeleteApplicationCommand extends AbstractDeleteCommand {
    public static final String COMMAND_WORD = "delete_app";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a selected job application from "
        + "the list of job applications \n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 2";
    public static final String MESSAGE_CONFIRM_STAGE = "Delete application selected. Proceed? (Y/N)";
    public static final String MESSAGE_DELETE_APPLICATION_CONFIRM = "Job application for candidate \"%1$s\" "
        + "applying for job \"%2$s\" successfully deleted!";

    public static final String MESSAGE_DELETE_APPLICATION_CANCEL = "Deletion of application aborted";
    private final Index targetIndex;


    public NewDeleteApplicationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownApplicationList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownApplicationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToDelete = lastShownApplicationList.get(targetIndex.getZeroBased());
        model.setLastMentionedApplication(applicationToDelete);
        return new CommandResult(MESSAGE_CONFIRM_STAGE, CommandBoxState.CONFIRM);
    }


    @Override
    public String performDelete(Model model) throws CommandException {
        requireNonNull(model);
        Application applicationToDelete = model.getLastMentionedApplication().get();
        model.deleteApplication(applicationToDelete);
        return String.format(MESSAGE_DELETE_APPLICATION_CONFIRM,
            applicationToDelete.getPerson().getName(), applicationToDelete.getJob().title);
    }

    @Override
    public String denyDelete(Model model) {
        return MESSAGE_DELETE_APPLICATION_CANCEL;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NewDeleteApplicationCommand)) {
            return false;
        }
        NewDeleteApplicationCommand o = (NewDeleteApplicationCommand) other;
        return (this.targetIndex.equals(o.targetIndex));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}

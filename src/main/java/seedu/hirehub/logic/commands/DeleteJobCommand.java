package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.job.Job;

/**
 * Deletes a job identified using its displayed index from list of jobs
 */
public class DeleteJobCommand extends AbstractDeleteCommand {
    public static final String COMMAND_WORD = "delete_job";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the job identified by the index number used in the displayed job list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_JOB_CONFIRM = "Deleted Job: %1$s";

    public static final String MESSAGE_DELETE_JOB_CANCEL = "Deletion of job aborted";

    public static final String MESSAGE_CONFIRM_STAGE = "Delete job selected. Proceed? (Y/N)";
    private final Index targetIndex;

    public DeleteJobCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Job> lastShownJobList = model.getFilteredJobList();

        if (targetIndex.getZeroBased() >= lastShownJobList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        Job jobToDelete = lastShownJobList.get(targetIndex.getZeroBased());
        model.setLastMentionedJob(jobToDelete);
        return new CommandResult(MESSAGE_CONFIRM_STAGE, CommandBoxState.CONFIRM);
    }

    @Override
    public String performDelete(Model model) throws CommandException {
        requireNonNull(model);
        Job jobToDelete = model.getLastMentionedJob().get();
        model.deleteJob(jobToDelete);
        model.removeApplications(jobToDelete);
        return String.format(MESSAGE_DELETE_JOB_CONFIRM, Messages.format(jobToDelete));
    }

    @Override
    public String denyDelete(Model model) {
        return MESSAGE_DELETE_JOB_CANCEL;
    }
}

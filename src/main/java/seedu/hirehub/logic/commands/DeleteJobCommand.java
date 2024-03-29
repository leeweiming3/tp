package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.job.Job;

/**
 * Deletes a job identified using its displayed index from list of jobs
 */
public class DeleteJobCommand extends Command {

    public static final String COMMAND_WORD = "delete_job";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job identified by the index number used in the displayed job list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_JOB_SUCCESS = "Deleted Job: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Job jobToDelete = model.getLastMentionedJob().get();
        model.deleteJob(jobToDelete);
        model.removeApplications(jobToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_JOB_SUCCESS, Messages.format(jobToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteJobCommand)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}

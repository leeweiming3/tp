package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.job.Job;

/**
 * Pushes the program into the state to delete the job at targetIndex.
 */
public class InitDeleteJobCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Delete job selected. Proceed? (Y/N)";
    private final Index targetIndex;


    public InitDeleteJobCommand(Index targetIndex) {
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
        return new CommandResult(MESSAGE_SUCCESS, CommandBoxState.DELETEJOBCONFIRM);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InitDeleteJobCommand)) {
            return false;
        }
        InitDeleteJobCommand o = (InitDeleteJobCommand) other;
        return (this.targetIndex.equals(o.targetIndex));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}


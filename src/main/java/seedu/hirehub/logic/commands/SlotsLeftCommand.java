package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.job.Job;

/**
 * Adds an job application from a candidate to the list of job applications
 */
public class SlotsLeftCommand extends Command {
    public static final String COMMAND_WORD = "slots_left";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the number of slots left for the selected"
            + " job computed by subtracting the number of accepted candidates from total vacancies \n"
            + "Parameters: INDEX (index of the job displayed in the job list which must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";
    public static final String SLOTS_LEFT_SUCCESS = "There are %1$d vacancies left for job \"%2$s\"";

    private final Index index;

    public SlotsLeftCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Job> lastShownJobList = model.getFilteredJobList();

        if (index.getZeroBased() >= lastShownJobList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        Job jobToFindVacancy = model.getFilteredJobList().get(index.getZeroBased());
        int vacancyLeft = model.countRemainingVacancy(jobToFindVacancy.getTitle());

        return new CommandResult(String.format(SLOTS_LEFT_SUCCESS, vacancyLeft, jobToFindVacancy.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SlotsLeftCommand)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}

package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.List;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.status.Status;

/**
 * Updates recruitment status for candidates in the address book.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update status for an application within "
            + "the application list to one of the following 5 statuses:\n"
            + "PRESCREEN, IN_PROGRESS, WAITLIST, ACCEPTED, REJECTED\n"
            + "Parameters: INDEX (must be a positive number) STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 ACCEPTED";

    public static final String MESSAGE_STATUS_PERSON_SUCCESS = "Status of Candidate Successfully"
            + " Updated to %1$s";

    public static final String MESSAGE_DUPLICATE_STATUS = "This candidate with identical recruitment status %1$s "
            + "already exists in the application list";

    public static final String MESSAGE_EXCEEDS_VACANCY = "The number of accepted candidates already meets the"
            + " stipulated vacancy.\nTo accept more candidates, the vacancy for the job can be increased via the"
            + " edit_job command, or change status for existing application(s) to this job via status command to"
            + " a status other than ACCEPTED.\nYou can retrieve vacancies left via slots_left command";
    private final Index index;
    private final Status status;

    /**
     * Creates an StatusCommand to update the candidate status for specified {@code Person}
     */
    public StatusCommand(Index index, Status status) {
        requireNonNull(index);
        requireNonNull(status);
        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownApplicationList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownApplicationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToUpdate = lastShownApplicationList.get(index.getZeroBased());

        if (status.equals(applicationToUpdate.status)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_STATUS, status));
        }

        if (status.equals(new Status("ACCEPTED"))
                && model.countRemainingVacancy(applicationToUpdate.getJob().getTitle()) <= 0) {
            throw new CommandException(MESSAGE_EXCEEDS_VACANCY);
        }

        Application editedApplication = new Application(applicationToUpdate.getPerson(),
                applicationToUpdate.getJob(), status);

        model.setApplication(applicationToUpdate, editedApplication);
        model.updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
        return new CommandResult(String.format(MESSAGE_STATUS_PERSON_SUCCESS, status));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatusCommand)) {
            return false;
        }

        StatusCommand otherStatusCommand = (StatusCommand) other;
        return index.equals(otherStatusCommand.index)
                && status.equals(otherStatusCommand.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("status", status)
                .toString();
    }
}

package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_VACANCY;
import static seedu.hirehub.model.Model.PREDICATE_SHOW_ALL_JOBS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.commons.util.CollectionUtil;
import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.Model;

/**
 * Edits the details of an existing job in the address book.
 */
public class EditJobCommand extends Command {

    public static final String COMMAND_WORD = "edit_job";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the job identified "
            + "by the index number used in the displayed job list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_VACANCY + "VACANCY] ";

    public static final String MESSAGE_EDIT_JOB_SUCCESS = "Edited Job: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book.";

    private final Index index;
    private final EditJobDescriptor editJobDescriptor;

    /**
     * @param index of the job in the filtered job list to edit
     * @param editJobDescriptor details to edit the job with
     */
    public EditJobCommand(Index index, EditJobDescriptor editJobDescriptor) {
        requireNonNull(index);
        requireNonNull(editJobDescriptor);

        this.index = index;
        this.editJobDescriptor = new EditJobDescriptor(editJobDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Job> lastShownList = model.getFilteredJobList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        Job jobToEdit = lastShownList.get(index.getZeroBased());
        Job editedJob = createEditedJob(jobToEdit, editJobDescriptor);

        if (!jobToEdit.isSameJob(editedJob) && model.hasJob(editedJob)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

        model.setJob(jobToEdit, editedJob);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        model.replaceApplications(jobToEdit, editedJob);
        return new CommandResult(String.format(MESSAGE_EDIT_JOB_SUCCESS, editedJob));
    }

    /**
     * Creates and returns a {@code Job} with the details of {@code jobToEdit}
     * edited with {@code editJobDescriptor}.
     */
    private static Job createEditedJob(Job jobToEdit, EditJobDescriptor editJobDescriptor) {
        assert jobToEdit != null;

        String updatedTitle = editJobDescriptor.getTitle().orElse(jobToEdit.getTitle());
        String updatedDescription = editJobDescriptor.getDescription().orElse(jobToEdit.getDescription());
        int updatedVacancy = editJobDescriptor.getVacancy().orElse(jobToEdit.getVacancy());

        return new Job(updatedTitle, updatedDescription, updatedVacancy);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditJobCommand)) {
            return false;
        }

        EditJobCommand otherEditJobCommand = (EditJobCommand) other;
        return index.equals(otherEditJobCommand.index)
                && editJobDescriptor.equals(otherEditJobCommand.editJobDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editJobDescriptor", editJobDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the job with. Each non-empty field value will replace the
     * corresponding field value of the job.
     */
    public static class EditJobDescriptor {
        private String title;
        private String description;
        private Integer vacancy;

        public EditJobDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditJobDescriptor(EditJobDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setVacancy(toCopy.vacancy);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title,description,vacancy);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setVacancy(Integer vacancy) {
            this.vacancy = vacancy;
        }

        public Optional<Integer> getVacancy() {
            return Optional.ofNullable(vacancy);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditJobDescriptor)) {
                return false;
            }

            EditJobDescriptor otherEditJobDescriptor = (EditJobDescriptor) other;
            return Objects.equals(title, otherEditJobDescriptor.title)
                    && Objects.equals(description, otherEditJobDescriptor.description)
                    && Objects.equals(vacancy, otherEditJobDescriptor.vacancy);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("title", title)
                    .add("description", description)
                    .add("vacancy", vacancy)
                    .toString();
        }
    }
}

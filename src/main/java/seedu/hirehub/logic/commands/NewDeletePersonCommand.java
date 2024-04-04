package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.commons.util.ToStringBuilder;
import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.person.Person;

/**
 * Pushes the program into the state to delete the person at targetIndex.
 */
public class NewDeletePersonCommand extends AbstractDeleteCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the person identified by the index number used in the displayed person list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_CONFIRM_STAGE = "Delete person. Proceed? (Y/N)";

    public static final String MESSAGE_DELETE_PERSON_CONFIRM = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_PERSON_CANCEL = "Deletion aborted";
    private final Index targetIndex;

    public NewDeletePersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.setLastMentionedPerson(personToDelete);
        return new CommandResult(MESSAGE_CONFIRM_STAGE, CommandBoxState.CONFIRM);
    }

    @Override
    public String performDelete(Model model) {
        requireNonNull(model);
        Person personToDelete = model.getLastMentionedPerson().get();
        model.deletePerson(personToDelete);
        model.removeApplications(personToDelete);
        return String.format(MESSAGE_DELETE_PERSON_CONFIRM, Messages.format(personToDelete));
    }

    @Override
    public String denyDelete(Model model) {
        return MESSAGE_DELETE_PERSON_CANCEL;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NewDeletePersonCommand)) {
            return false;
        }
        NewDeletePersonCommand o = (NewDeletePersonCommand) other;
        return (this.targetIndex.equals(o.targetIndex));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
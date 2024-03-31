package seedu.hirehub.logic.commands;

import static seedu.hirehub.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.hirehub.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.tag.Tag;

/**
 * Deletes tags from an existing person in the address book.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "delete_tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes tags from the person identified "
            + "by the index number used in the last person listing.\n"
            + "At least one tag must be present. All tags specified will be removed.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "INTERNAL " + PREFIX_TAG + "WAITLIST";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed tags from Person: %1$s";
    public static final String MESSAGE_TAG_NOT_PRESENT = "Some tags are not present on the person.";

    private final Index index;
    private final Set<Tag> tags;

    /**
     * @param index of the person in the filtered person list to edit the comment
     * @param tags to be removed from the person
     */
    public DeleteTagCommand(Index index, Set<Tag> tags) {
        requireAllNonNull(index, tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Tag> newTagList = new HashSet<>(personToEdit.getTags());
        for (Tag t : tags) {
            if (newTagList.contains(t)) {
                newTagList.remove(t);
            } else {
                throw new CommandException(DeleteTagCommand.MESSAGE_TAG_NOT_PRESENT);
            }
        }
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getCountry(), personToEdit.getComment(), newTagList);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return index.equals(otherDeleteTagCommand.index)
                && tags.equals(otherDeleteTagCommand.tags);
    }
}

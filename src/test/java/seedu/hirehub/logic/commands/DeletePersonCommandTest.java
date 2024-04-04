package seedu.hirehub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hirehub.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.hirehub.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.hirehub.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.hirehub.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.ModelManager;
import seedu.hirehub.model.UserPrefs;
import seedu.hirehub.model.job.UniqueJobList;
import seedu.hirehub.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePersonCommand}.
 */
public class DeletePersonCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UniqueJobList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        DeletePersonCommand deleteCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_CONFIRM_STAGE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
            new UniqueJobList(), new UserPrefs());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePersonCommand deleteCommand = new DeletePersonCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePersonCommand deleteCommand = new DeletePersonCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePersonCommand initdeleteFirstCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);
        DeletePersonCommand initdeleteSecondCommand = new DeletePersonCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(initdeleteFirstCommand, initdeleteFirstCommand);

        // same values -> returns true
        DeletePersonCommand deleteFirstCommandCopy = new DeletePersonCommand(INDEX_FIRST_PERSON);
        assertEquals(initdeleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, initdeleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, initdeleteFirstCommand);

        // different person -> returns false
        assertNotEquals(initdeleteFirstCommand, initdeleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeletePersonCommand deleteCommand = new DeletePersonCommand(targetIndex);
        String expected = DeletePersonCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void execute_validIndexUnfilteredList_delete_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.setLastMentionedPerson(personToDelete);
        Command deleteCommand = new DeletePersonCommand(INDEX_FIRST_PERSON).whenConfirmed();

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_CONFIRM,
            Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
            new UniqueJobList(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_delete_success() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.setLastMentionedPerson(personToDelete);
        Command deleteCommand = new DeletePersonCommand(INDEX_FIRST_PERSON).whenConfirmed();

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_CONFIRM,
            Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(),
            new UniqueJobList(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}

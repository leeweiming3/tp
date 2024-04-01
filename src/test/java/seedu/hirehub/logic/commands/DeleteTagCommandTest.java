package seedu.hirehub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hirehub.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.hirehub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hirehub.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.hirehub.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.hirehub.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.hirehub.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.Messages;
import seedu.hirehub.logic.parser.ParserUtil;
import seedu.hirehub.logic.parser.exceptions.ParseException;
import seedu.hirehub.model.AddressBook;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.ModelManager;
import seedu.hirehub.model.UserPrefs;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.tag.Tag;
import seedu.hirehub.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteTagCommand.
 */
class DeleteTagCommandTest {
    private static final String TAG_1 = "owesMoney";
    private static final String TAG_2 = "friends";
    private static final String TAG_3 = "enthusiastic";

    private static final String TAG_4 = "spiffy";
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_deleteTagUnfilteredList_success() {

        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        HashSet<Tag> editedTags = new HashSet<>(secondPerson.getTags());
        assertEquals(editedTags, stringsToTags(TAG_1, TAG_2, TAG_3));
        editedTags.remove(new Tag(TAG_1));
        Person editedPerson = new PersonBuilder(secondPerson)
                .withTags(editedTags).build();

        DeleteTagCommand delCommand = new DeleteTagCommand(INDEX_SECOND_PERSON,
                stringsToTags(TAG_1));

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(delCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_deleteMultipleTagUnfilteredList_success() {

        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        HashSet<Tag> editedTags = new HashSet<>(secondPerson.getTags());
        assertEquals(editedTags, stringsToTags(TAG_1, TAG_2, TAG_3));
        editedTags.remove(new Tag(TAG_1));
        editedTags.remove(new Tag(TAG_2));
        Person editedPerson = new PersonBuilder(secondPerson)
                .withTags(editedTags).build();

        DeleteTagCommand delCommand = new DeleteTagCommand(INDEX_SECOND_PERSON,
                stringsToTags(TAG_1, TAG_2));

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(delCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person secondPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        HashSet<Tag> editedTags = stringsToTags(TAG_2, TAG_3);

        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()))
                .withTags(editedTags).build();

        DeleteTagCommand delCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, stringsToTags(TAG_1));
        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(delCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingTags_failure() {

        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertFalse(secondPerson.getTags().contains(new Tag(TAG_4)));

        DeleteTagCommand delCommand = new DeleteTagCommand(INDEX_SECOND_PERSON,
                stringsToTags(TAG_1, TAG_4));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandFailure(delCommand, model, DeleteTagCommand.MESSAGE_TAG_NOT_PRESENT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTagCommand delCommand = new DeleteTagCommand(outOfBoundIndex, stringsToTags(TAG_2, TAG_3));

        assertCommandFailure(delCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteTagCommand delCommand = new DeleteTagCommand(outOfBoundIndex, stringsToTags());

        assertCommandFailure(delCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws ParseException {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_SECOND_PERSON,
                ParserUtil.parseTags(List.of(VALID_TAG_FRIEND)));
        // same values -> returns true
        final DeleteTagCommand commandWithSameValues = new DeleteTagCommand(INDEX_SECOND_PERSON,
                ParserUtil.parseTags(List.of(VALID_TAG_FRIEND)));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_PERSON,
                ParserUtil.parseTags(List.of(VALID_TAG_FRIEND)))));
        // different tag -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON,
                ParserUtil.parseTags(List.of(VALID_TAG_HUSBAND)))));
    }

    private HashSet<Tag> stringsToTags(String... tags) {
        HashSet<Tag> hs = new HashSet<>();
        for (String t : tags) {
            hs.add(new Tag(t));
        }
        return hs;
    }
}

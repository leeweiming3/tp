package seedu.hirehub.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hirehub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hirehub.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.hirehub.testutil.Assert.assertThrows;
import static seedu.hirehub.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.hirehub.logic.commands.AddCommand;
import seedu.hirehub.logic.commands.ClearCommand;
import seedu.hirehub.logic.commands.DeletePersonCommand;
import seedu.hirehub.logic.commands.EditCommand;
import seedu.hirehub.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.hirehub.logic.commands.ExitCommand;
import seedu.hirehub.logic.commands.HelpCommand;
import seedu.hirehub.logic.commands.ListCommand;
import seedu.hirehub.logic.parser.exceptions.ParseException;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.testutil.EditPersonDescriptorBuilder;
import seedu.hirehub.testutil.PersonBuilder;
import seedu.hirehub.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
            DeletePersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

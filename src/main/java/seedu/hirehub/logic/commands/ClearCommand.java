package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.model.AddressBook;
import seedu.hirehub.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.clearApplications();
        // TODO: remove print statements once UI is up and running
        System.out.println(model.getApplicationList());
        System.out.println(model.getFilteredApplicationList());
        return new CommandResult(MESSAGE_SUCCESS, CommandBoxState.NORMAL);
    }
}

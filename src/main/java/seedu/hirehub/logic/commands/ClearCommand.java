package seedu.hirehub.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.model.AddressBook;
import seedu.hirehub.model.Model;

/**
 * Pushes the program into the state to clear the address book.
 */
public class ClearCommand extends AbstractDeleteCommand {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CONFIRM_STAGE = "Clearing the address book is irreversible. Proceed? (Y/N)";
    public static final String MESSAGE_ADDRESS_BOOK_EMPTY = "The address book is empty.";

    public static final String MESSAGE_CLEAR_CONFIRM = "Address book has been cleared!";

    public static final String MESSAGE_CLEAR_CANCEL = "Clearing aborted";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getAddressBook().getPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_ADDRESS_BOOK_EMPTY, CommandBoxState.NORMAL);
        }
        return new CommandResult(MESSAGE_CONFIRM_STAGE, CommandBoxState.CONFIRM);
    }

    @Override
    public String performDelete(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.clearApplications();
        return MESSAGE_CLEAR_CONFIRM;

    }

    @Override
    public String denyDelete(Model model) {
        return MESSAGE_CLEAR_CANCEL;
    }
}

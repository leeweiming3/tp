package seedu.hirehub.logic.commands;

import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hirehub.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.hirehub.model.AddressBook;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.ModelManager;
import seedu.hirehub.model.UserPrefs;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.UniqueJobList;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand().whenConfirmed(),
            model, ClearCommand.MESSAGE_CLEAR_CONFIRM, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UniqueJobList(), new UserPrefs(),
            new UniqueApplicationList());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UniqueJobList(), new UserPrefs(),
            new UniqueApplicationList());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand().whenConfirmed(),
            model, ClearCommand.MESSAGE_CLEAR_CONFIRM, expectedModel);
    }

}

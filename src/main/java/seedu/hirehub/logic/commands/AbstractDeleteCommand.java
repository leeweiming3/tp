package seedu.hirehub.logic.commands;

import seedu.hirehub.logic.CommandBoxState;
import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;

/**
 * Represents a deletion command, which only executes upon confirmation
 */
public abstract class AbstractDeleteCommand extends ConfirmableCommand {
    /**
     * Execute delete command on confirmation, on model
     *
     * @param model Model to operate on
     * @return response string
     */
    public abstract String performDelete(Model model) throws CommandException;

    /**
     * Execute denied delete command on model
     *
     * @param model Model to operate on
     * @return response string
     */
    public abstract String denyDelete(Model model);

    @Override
    public Command whenConfirmed() {
        return new DeleteCommand();
    }

    @Override
    public Command whenDenied() {
        return new AbortDeleteCommand();
    }

    class DeleteCommand extends Command {
        @Override
        public CommandResult execute(Model model) throws CommandException {
            String result = AbstractDeleteCommand.this.performDelete(model);
            return new CommandResult(result, CommandBoxState.NORMAL);
        }
    }

    class AbortDeleteCommand extends Command {
        @Override
        public CommandResult execute(Model model) throws CommandException {
            String result = AbstractDeleteCommand.this.denyDelete(model);
            return new CommandResult(result, CommandBoxState.NORMAL);
        }
    }
}

package seedu.hirehub.logic.commands;

import seedu.hirehub.logic.commands.exceptions.CommandException;
import seedu.hirehub.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Returns the command to be executed upon next confirmation.
     * Only applicable for Commands with a confirmation stage.
     */
    public Command whenConfirmed() {
        return null;
    }

    /**
     * Returns the command to be executed upon next refusal to confirm.
     * Only applicable for Commands with a confirmation stage.
     */
    public Command whenDenied() {
        return null;
    }
}

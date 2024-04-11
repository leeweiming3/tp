package seedu.hirehub.logic.commands;

/**
 * Represents a command which puts the parser in confirm mode and executes
 * more commands upon confirm/deny.
 */
public abstract class ConfirmableCommand extends Command {
    /**
     * Returns the command to be executed upon next confirmation.
     */
    public Command whenConfirmed() {
        return null;
    }

    /**
     * Returns the command to be executed upon next refusal to confirm.
     */
    public Command whenDenied() {
        return null;
    }
}

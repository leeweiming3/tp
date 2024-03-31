package seedu.hirehub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.commands.SlotsLeftCommand;
import seedu.hirehub.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SlotsLeftCommandParser object
 */
public class SlotsLeftCommandParser implements Parser<SlotsLeftCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SlotsLeftCommand
     * and returns a SlotsLeftCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SlotsLeftCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SlotsLeftCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SlotsLeftCommand.MESSAGE_USAGE), pe);
        }
    }
}

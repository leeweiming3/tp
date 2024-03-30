package seedu.hirehub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.commands.StatusCommand;
import seedu.hirehub.logic.parser.exceptions.ParseException;
import seedu.hirehub.model.status.Status;

/**
 * Parses input arguments and creates a new StatusCommand object
 */
public class StatusCommandParser implements Parser<StatusCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the StatusCommand
     * and returns a StatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        String trimmedPreamble = argMultimap.getPreamble().trim();

        if (!arePrefixesPresent(argMultimap, PREFIX_STATUS) || trimmedPreamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StatusCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STATUS);
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());

        try {
            Index index = ParserUtil.parseIndex(trimmedPreamble);
            return new StatusCommand(index, status);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StatusCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

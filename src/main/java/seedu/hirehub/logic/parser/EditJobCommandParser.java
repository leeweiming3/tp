package seedu.hirehub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_VACANCY;

import seedu.hirehub.commons.core.index.Index;
import seedu.hirehub.logic.commands.EditJobCommand;
import seedu.hirehub.logic.commands.EditJobCommand.EditJobDescriptor;
import seedu.hirehub.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditJobCommandParser implements Parser<EditJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditJobCommand
     * and returns an EditJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditJobCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE,
                PREFIX_DESCRIPTION, PREFIX_VACANCY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditJobCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_VACANCY);

        EditJobDescriptor editJobDescriptor = new EditJobDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editJobDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editJobDescriptor.setDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get().trim());
        }
        if (argMultimap.getValue(PREFIX_VACANCY).isPresent()) {
            editJobDescriptor.setVacancy(ParserUtil.parseVacancy(argMultimap.getValue(PREFIX_VACANCY).get()));
        }

        if (!editJobDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditJobCommand.MESSAGE_NOT_EDITED);
        }

        return new EditJobCommand(index, editJobDescriptor);
    }
}

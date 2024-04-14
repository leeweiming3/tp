package seedu.hirehub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.hirehub.logic.commands.SearchCommand;
import seedu.hirehub.logic.commands.SearchCommand.SearchPersonDescriptor;
import seedu.hirehub.logic.parser.exceptions.ParseException;
import seedu.hirehub.model.person.Comment;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COUNTRY,
                        PREFIX_COMMENT, PREFIX_TAG);
        String trimmedArgs = args.trim();
        String trimmedPreamble = argMultimap.getPreamble().trim();
        if (!trimmedPreamble.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(SearchCommand.MESSAGE_NO_FIELD_PROVIDED);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COUNTRY,
                PREFIX_COMMENT, PREFIX_TAG);

        SearchPersonDescriptor searchPersonDescriptor = new SearchPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            searchPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            searchPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            searchPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_COUNTRY).isPresent()) {
            searchPersonDescriptor.setCountry(ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get()));
        }
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            searchPersonDescriptor.setComment(new Comment(argMultimap.getValue(PREFIX_COMMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            searchPersonDescriptor.setTags(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));
        }

        return new SearchCommand(searchPersonDescriptor);
    }
}

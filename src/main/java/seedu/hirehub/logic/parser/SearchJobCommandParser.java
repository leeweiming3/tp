package seedu.hirehub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_VACANCY;

import java.util.ArrayList;
import java.util.Optional;

import seedu.hirehub.logic.commands.SearchJobCommand;
import seedu.hirehub.logic.parser.exceptions.ParseException;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.ContainsKeywordsPredicate;
import seedu.hirehub.model.person.SearchPredicate;

/**
 * Parses input arguments and creates a new SearchApplicationCommand object
 */
public class SearchJobCommandParser implements Parser<SearchJobCommand> {


    @Override
    public SearchJobCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_VACANCY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SearchJobCommand.MESSAGE_USAGE));
        }

        if (args.trim().isEmpty()) {
            throw new ParseException(SearchJobCommand.MESSAGE_NO_FIELD_PROVIDED);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_VACANCY);

        ArrayList<ContainsKeywordsPredicate<Job, ?>> predicateList = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            ContainsKeywordsPredicate<Job, String> titleSearch =
                    new ContainsKeywordsPredicate<>(PREFIX_TITLE, Optional.of(title));
            predicateList.add(titleSearch);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            ContainsKeywordsPredicate<Job, String> descriptionSearch =
                    new ContainsKeywordsPredicate<>(PREFIX_DESCRIPTION, Optional.of(description));
            predicateList.add(descriptionSearch);
        }

        if (argMultimap.getValue(PREFIX_VACANCY).isPresent()) {
            int vacancy = ParserUtil.parseVacancy(argMultimap.getValue(PREFIX_VACANCY).get());
            ContainsKeywordsPredicate<Job, Integer> vacancySearch =
                    new ContainsKeywordsPredicate<>(PREFIX_VACANCY, Optional.of(vacancy));
            predicateList.add(vacancySearch);
        }

        return new SearchJobCommand(new SearchPredicate<>(predicateList));
    }
}

package seedu.hirehub.testutil;

import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Optional;

import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.ContainsKeywordsPredicate;
import seedu.hirehub.model.person.Email;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.person.SearchPredicate;
import seedu.hirehub.model.status.Status;

/**
 * A utility class to help with building SearchApplicationPredicate objects.
 */
public class SearchApplicationPredicateBuilder {

    private Optional<ContainsKeywordsPredicate<Application, String>> titlePredicate;
    private Optional<ContainsKeywordsPredicate<Application, Email>> emailPredicate;
    private Optional<ContainsKeywordsPredicate<Application, Status>> statusPredicate;

    /**
     * Instantiates SearchApplicationPredicateBuilder instance
     */
    public SearchApplicationPredicateBuilder() {
        titlePredicate = Optional.empty();
        emailPredicate = Optional.empty();
        statusPredicate = Optional.empty();
    }

    /**
     * Returns an arraylist of predicates with fields containing {@code application}'s details
     */
    public SearchApplicationPredicateBuilder(Application application) {
        titlePredicate = Optional.of(
                new ContainsKeywordsPredicate<>(PREFIX_TITLE, Optional.of(application.getJob().getTitle())));
        emailPredicate = Optional.of(
                new ContainsKeywordsPredicate<>(PREFIX_EMAIL, Optional.of(application.getPerson().getEmail())));
        statusPredicate = Optional.of(
                new ContainsKeywordsPredicate<>(PREFIX_STATUS, Optional.of(application.getStatus())));
    }

    /**
     * Sets the {@code titlePredicate} with the given String input for title such that
     * the ContainsKeywordsPredicate contains Optional of a job title.
     */
    public SearchApplicationPredicateBuilder withJob(Job job) {
        titlePredicate = titlePredicate.of(new ContainsKeywordsPredicate<>(PREFIX_TITLE, Optional.of(job.getTitle())));
        return this;
    }

    /**
     * Sets the {@code emailPredicate} with the email of given person's input such that
     * the ContainsKeywordsPredicate contains Optional of an email.
     */
    public SearchApplicationPredicateBuilder withPerson(Person person) {
        emailPredicate = emailPredicate.of(
                new ContainsKeywordsPredicate<>(PREFIX_EMAIL, Optional.of(person.getEmail())));
        return this;
    }

    /**
     * Sets the {@code statusPredicate} with the given Status input such that
     * the ContainsKeywordsPredicate contains Optional of a status.
     */
    public SearchApplicationPredicateBuilder withStatus(Status status) {
        statusPredicate = statusPredicate.of(
                new ContainsKeywordsPredicate<>(PREFIX_STATUS, Optional.of(status)));
        return this;
    }

    /**
     * Convert Optional of predicates of title, email and status into SearchPredicate object containing
     * those predicates
     */
    public SearchPredicate<Application> build() {
        ArrayList<ContainsKeywordsPredicate<Application, ?>> predicateList = new ArrayList<>();
        if (!titlePredicate.isEmpty()) {
            predicateList.add(titlePredicate.get());
        }
        if (!emailPredicate.isEmpty()) {
            predicateList.add(emailPredicate.get());
        }
        if (!statusPredicate.isEmpty()) {
            predicateList.add(statusPredicate.get());
        }
        return new SearchPredicate<Application>(predicateList);
    }
}

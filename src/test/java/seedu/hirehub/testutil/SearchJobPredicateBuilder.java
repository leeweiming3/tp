package seedu.hirehub.testutil;

import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.hirehub.logic.parser.CliSyntax.PREFIX_VACANCY;

import java.util.ArrayList;
import java.util.Optional;

import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.ContainsKeywordsPredicate;
import seedu.hirehub.model.person.SearchPredicate;

/**
 * A utility class to help with building SearchJobPredicate objects.
 */
public class SearchJobPredicateBuilder {

    private Optional<ContainsKeywordsPredicate<Job, String>> titlePredicate;
    private Optional<ContainsKeywordsPredicate<Job, String>> descriptionPredicate;
    private Optional<ContainsKeywordsPredicate<Job, Integer>> vacancyPredicate;

    /**
     * Instantiates SearchJobPredicateBuilder instance
     */
    public SearchJobPredicateBuilder() {
        titlePredicate = Optional.empty();
        descriptionPredicate = Optional.empty();
        vacancyPredicate = Optional.empty();
    }

    /**
     * Returns an arraylist of job predicates with fields containing {@code job}'s details
     */
    public SearchJobPredicateBuilder(Job job) {
        titlePredicate = Optional.of(
                new ContainsKeywordsPredicate<>(PREFIX_TITLE, Optional.of(job.getTitle())));
        descriptionPredicate = Optional.of(
                new ContainsKeywordsPredicate<>(PREFIX_DESCRIPTION, Optional.of(job.getDescription())));
        vacancyPredicate = Optional.of(
                new ContainsKeywordsPredicate<>(PREFIX_VACANCY, Optional.of(job.getVacancy())));
    }

    /**
     * Sets the {@code titlePredicate} with the given String input for title such that
     * the ContainsKeywordsPredicate contains Optional of a job title.
     */
    public SearchJobPredicateBuilder withTitle(String title) {
        titlePredicate = titlePredicate.of(new ContainsKeywordsPredicate<>(PREFIX_TITLE, Optional.of(title)));
        return this;
    }

    /**
     * Sets the {@code descriptionPredicate} with the given String input for description such that
     * the ContainsKeywordsPredicate contains Optional of a job description.
     */
    public SearchJobPredicateBuilder withDescription(String description) {
        descriptionPredicate = descriptionPredicate.of(
                new ContainsKeywordsPredicate<>(PREFIX_DESCRIPTION, Optional.of(description)));
        return this;
    }

    /**
     * Sets the {@code vacancyPredicate} with the given Integer input for vacancy such that
     * the ContainsKeywordsPredicate contains Optional of a job vacancy.
     */
    public SearchJobPredicateBuilder withVacancy(int vacancy) {
        vacancyPredicate = vacancyPredicate.of(
                new ContainsKeywordsPredicate<>(PREFIX_VACANCY, Optional.of(vacancy)));
        return this;
    }

    /**
     * Convert Optional of predicates of title, description and vacancy into SearchPredicate object containing
     * those predicates
     */
    public SearchPredicate<Job> build() {
        ArrayList<ContainsKeywordsPredicate<Job, ?>> predicateList = new ArrayList<>();
        if (!titlePredicate.isEmpty()) {
            predicateList.add(titlePredicate.get());
        }
        if (!descriptionPredicate.isEmpty()) {
            predicateList.add(descriptionPredicate.get());
        }
        if (!vacancyPredicate.isEmpty()) {
            predicateList.add(vacancyPredicate.get());
        }
        return new SearchPredicate<Job>(predicateList);
    }
}

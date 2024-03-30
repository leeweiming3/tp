package seedu.hirehub.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that all a {@code Person}'s {@code Attributes} matches the corresponding keywords given.
 */
public class SearchPredicate<T> implements Predicate<T> {
    private final List<ContainsKeywordsPredicate<T, ?>> predicateList;

    public SearchPredicate(List<ContainsKeywordsPredicate<T, ?>> predicateList) {
        this.predicateList = predicateList;
    }

    @Override
    public boolean test(T items) {
        boolean result = true;
        for (ContainsKeywordsPredicate<T, ?> predicate : predicateList) {
            result = result && predicate.test(items);
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchPredicate)) {
            return false;
        }

        SearchPredicate<T> otherSearchPredicate = (SearchPredicate<T>) other;
        return predicateList.equals(otherSearchPredicate.predicateList);
    }
}

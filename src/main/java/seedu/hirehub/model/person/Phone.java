package seedu.hirehub.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "International phone numbers should only contain a country code "
                    + "in front (+ followed by 1 to 3 digits), then a space, "
                    + "followed by a combination of digits, spaces, "
                    + "parentheses or hyphens with at least 3 digits.\n"
                    + "If country code is omitted, it must be a valid Singapore phone number.";

    public static final String INTERNATIONAL_VALIDATION_REGEX =
            "^\\+\\d{1,3} ((\\d|\\(|\\)|-| )*)\\d((\\d|\\(|\\)|-| )*)\\d((\\d|\\(|\\)|-| )*)\\d$";
    public static final String LOCAL_VALIDATION_REGEX = "^[3689]\\d{3}[- ]?\\d{4}$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(INTERNATIONAL_VALIDATION_REGEX) || test.matches(LOCAL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

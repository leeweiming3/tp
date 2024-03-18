package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        // local numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 8 digits
        assertFalse(Phone.isValidPhone("911348924")); // more than 8 digits
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p0418")); // alphabets within digits
        assertFalse(Phone.isValidPhone("12345678")); // does not start with "3", "6", "8" or "9"
        assertFalse(Phone.isValidPhone("93121 534")); // space does not split numbers into 2 blocks of 4
        assertFalse(Phone.isValidPhone("93121-534")); // hyphen does not split numbers into 2 blocks of 4
        assertFalse(Phone.isValidPhone("9011  0415")); // double whitespaces between digits

        // international numbers
        assertFalse(Phone.isValidPhone("+1 2")); // less than 3 digits in the suffix
        assertFalse(Phone.isValidPhone("+1234")); // no whitespace to denote country code
        assertFalse(Phone.isValidPhone("+ 123")); // less than 1 digit in country code
        assertFalse(Phone.isValidPhone("+1234 123")); // more than 3 digits in country code
        assertFalse(Phone.isValidPhone("+123 4e32")); // alphabets within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertTrue(Phone.isValidPhone("9312-1534")); // hyphens within digits
        assertTrue(Phone.isValidPhone("6411 6127"));
        assertTrue(Phone.isValidPhone("87081796"));
        assertTrue(Phone.isValidPhone("35261934"));
        assertTrue(Phone.isValidPhone("95261934"));
        //@@author {logical-1985516}-reused
        //Reused from https://codingnconcepts.com/java/java-regex-to-validate-phone-number/#regex-to-match-10-digit-phone-number-with-parentheses
        //with minor modifications
        assertTrue(Phone.isValidPhone("+995 442 123456"));
        assertTrue(Phone.isValidPhone("+93 30 539-0605"));
        assertTrue(Phone.isValidPhone("+61 2 1255-3456"));
        assertTrue(Phone.isValidPhone("+86 (20) 1255-3456"));
        assertTrue(Phone.isValidPhone("+49 351 125-3456"));
        assertTrue(Phone.isValidPhone("+62 21 6539-0605"));
        assertTrue(Phone.isValidPhone("+98 (515) 539-0605"));
        assertTrue(Phone.isValidPhone("+39 06 5398-0605"));
        assertTrue(Phone.isValidPhone("+64 3 539-0605"));
        assertTrue(Phone.isValidPhone("+63 35 539-0605"));
        assertTrue(Phone.isValidPhone("+39 06 5398-0605"));
        assertTrue(Phone.isValidPhone("+65 6396 0605"));
        assertTrue(Phone.isValidPhone("+66 2 123 4567"));
        assertTrue(Phone.isValidPhone("+44 141 222-3344"));
        assertTrue(Phone.isValidPhone("+1 (212) 555-3456"));
        assertTrue(Phone.isValidPhone("+84 35 539-0605"));
        //@@author
    }

    @Test
    public void equals() {
        Phone phone = new Phone("8123 4567");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("8123 4567")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("99599599")));
    }
}

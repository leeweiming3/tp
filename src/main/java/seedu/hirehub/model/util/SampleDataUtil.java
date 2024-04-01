package seedu.hirehub.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.hirehub.model.AddressBook;
import seedu.hirehub.model.ReadOnlyAddressBook;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.job.UniqueJobList;
import seedu.hirehub.model.person.Country;
import seedu.hirehub.model.person.Email;
import seedu.hirehub.model.person.Name;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.person.Phone;
import seedu.hirehub.model.status.Status;
import seedu.hirehub.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Person PERSON_1 = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
            new Country("SG"), getTagSet("friends"));
    private static final Job JOB_1 = new Job("Senior Software Engineer (Singapore)", "10 years of experience in C++, Singaporean citizen", 1);
    public static final Job JOB_2 = new Job("Software Engineer (London)", "Graduated with FCH, UK citizen", 5);

    public static Person[] getSamplePersons() {
        return new Person[] {
            PERSON_1,
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Country("CN"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Country("SG"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Country("SG"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Country("MY"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Country("IN"), getTagSet("colleagues"))
        };
    }

    public static Job[] getSampleJobs() {
        return new Job[] {
            JOB_1,
            new Job("Senior Software Engineer (London)", "10 years of experience in C++, UK citizen", 1),
            new Job("Software Engineer (Singapore)", "Graduated with FCH, Singaporean citizen", 5),
            JOB_2,
            new Job("Quantitative Trader", "Good at statistics and machine learning", 2),
            new Job("Data Analyst", "Adept at handling large datasets and data pipelines", 2)
        };
    }

    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(PERSON_1, JOB_1, new Status("PRESCREEN")),
            new Application(PERSON_1, JOB_2, new Status("IN_PROGRESS"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static UniqueJobList getSampleUniqueJobList() {
        UniqueJobList jobList = new UniqueJobList();
        for (Job job: getSampleJobs()) {
            jobList.addJob(job);
        }
        return jobList;
    }

    public static UniqueApplicationList getSampleUniqueApplicationList() {
        UniqueApplicationList applicationList = new UniqueApplicationList();
        for (Application a: getSampleApplications()) {
            applicationList.addApplication(a);
        }
        return applicationList;
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

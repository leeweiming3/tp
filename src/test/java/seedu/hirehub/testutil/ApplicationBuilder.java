package seedu.hirehub.testutil;

import static seedu.hirehub.testutil.TypicalJobs.QUANTITATIVE_RESEARCHER;
import static seedu.hirehub.testutil.TypicalPersons.ALICE;

import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.status.Status;

/**
 * A utility class to help with building Job objects.
 */
public class ApplicationBuilder {

    public static final Job DEFAULT_JOB = QUANTITATIVE_RESEARCHER;
    public static final Status DEFAULT_STATUS = new Status("PRESCREEN");
    public static final Person DEFAULT_PERSON = ALICE;

    private Job job;
    private Status status;
    private Person person;

    /**
     * Creates a {@code JobBuilder} with the default details.
     */
    public ApplicationBuilder() {
        job = DEFAULT_JOB;
        status = DEFAULT_STATUS;
        person = DEFAULT_PERSON;
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        job = applicationToCopy.getJob();
        status = applicationToCopy.getStatus();
        person = applicationToCopy.getPerson();
    }

    /**
     * Sets the {@code Job} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withJob(Job job) {
        this.job = job;
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Set the {@code person} to the {@code Application} that we are building.
     */
    public ApplicationBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    public Application build() {
        return new Application(person, job, status);
    }
}

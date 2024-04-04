package seedu.hirehub.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hirehub.commons.exceptions.IllegalValueException;
import seedu.hirehub.model.ReadOnlyAddressBook;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.job.UniqueJobList;
import seedu.hirehub.model.person.Email;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.status.Status;

/**
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";
    public static final String PERSON_NOT_FOUND_FORMAT = "Applicant email %s does not match any person!";
    public static final String JOB_NOT_FOUND_FORMAT = "Job title %s does not match any job!";
    private final String personEmail;
    private final String jobTitle;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("personEmail") String personEmail,
                                  @JsonProperty("jobTitle") String jobTitle,
                                  @JsonProperty("status") String status) {
        this.personEmail = personEmail;
        this.jobTitle = jobTitle;
        this.status = status;
    }

    /**
     * Converts a given {@code Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        personEmail = source.getPerson().getEmail().toString();
        jobTitle = source.getJob().getTitle();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted application.
     */
    public Application toModelType(UniqueJobList jobs,
                                   ReadOnlyAddressBook people) throws IllegalValueException {
        if (personEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "personEmail"));
        }
        if (jobTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "jobTitle"));
        }
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "status"));
        }

        if (!Email.isValidEmail(personEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        if (!Job.isValidTitle(jobTitle)) {
            throw new IllegalValueException(Job.TITLE_CONSTRAINTS);
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }

        Email email = new Email(personEmail);
        Person applicant = getPerson(people, email);
        if (applicant == null) {
            throw new IllegalValueException(String.format(PERSON_NOT_FOUND_FORMAT, email));
        }

        Job appliedJob = getJob(jobs);
        if (appliedJob == null) {
            throw new IllegalValueException(String.format(JOB_NOT_FOUND_FORMAT, jobTitle));
        }

        return new Application(applicant, appliedJob, new Status(status));
    }

    /**
     * Finds the job associated with the this.jobTitle.
     * Returns null if not found.
     *
     * @param jobs Joblist containing the job.
     * @return Job with correct name.
     */
    private Job getJob(UniqueJobList jobs) {
        for (Job j: jobs) {
            if (j.getTitle().equals(jobTitle)) {
                return j;
            }
        }
        return null;
    }

    /**
     * Finds the person associated with this email.
     * Returns null if not found.
     *
     * @param people List of people.
     * @param email  Email address to be found.
     */
    private static Person getPerson(ReadOnlyAddressBook people, Email email) {
        for (Person p: people.getPersonList()) {
            if (p.getEmail().equals(email)) {
                return p;
            }
        }
        return null;
    }
}

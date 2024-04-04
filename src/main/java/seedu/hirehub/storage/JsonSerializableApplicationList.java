package seedu.hirehub.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.hirehub.commons.exceptions.IllegalValueException;
import seedu.hirehub.model.ReadOnlyAddressBook;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.job.UniqueJobList;
import seedu.hirehub.model.status.Status;

/**
 * An immutable application list that is serializable to JSON format.
 */
@JsonRootName(value = "applications")
class JsonSerializableApplicationList {

    public static final String MESSAGE_DUPLICATE_APPLICATION =
        "This application already exists in the list of open applications";
    public static final String EXCEEDS_VACANCY_MESSAGE = "Accepted candidates for %s exceeds stipulated vacancy!";

    private final List<JsonAdaptedApplication> applications = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableApplicationList} with the given applications.
     */
    @JsonCreator
    public JsonSerializableApplicationList(@JsonProperty("applications") List<JsonAdaptedApplication> applications) {
        this.applications.addAll(applications);
    }

    /**
     * Converts a given {@code UniqueApplicationList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableApplicationList}.
     */
    public JsonSerializableApplicationList(UniqueApplicationList source) {
        for (Application application : source) {
            applications.add(new JsonAdaptedApplication(application));
        }
    }

    /**
     * Converts this application list into the model's {@code UniqueApplicationList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UniqueApplicationList toModelType(UniqueJobList jobs,
                                             ReadOnlyAddressBook people) throws IllegalValueException {

        HashMap<Job, Integer> offeredCount = new HashMap<>();
        UniqueApplicationList applicationList = new UniqueApplicationList();
        for (JsonAdaptedApplication jsonAdaptedApplication : applications) {
            Application application = jsonAdaptedApplication.toModelType(jobs, people);

            if (applicationList.containsApplication(application)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPLICATION);
            }
            if (application.getStatus().equals(new Status("OFFERED"))) {
                Job offeredJob = application.getJob();
                offeredCount.merge(offeredJob, 1, Integer::sum);
                if (offeredCount.get(offeredJob) > offeredJob.getVacancy()) {
                    throw new IllegalValueException(String.format(EXCEEDS_VACANCY_MESSAGE, offeredJob));
                }
            }

            applicationList.addApplication(application);
        }
        return applicationList;
    }

}

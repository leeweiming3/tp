package seedu.hirehub.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.hirehub.commons.exceptions.IllegalValueException;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.job.UniqueJobList;

/**
 * An immutable job list that is serializable to JSON format.
 */
@JsonRootName(value = "jobs")
class JsonSerializableJobList {

    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the list of open jobs";

    private final List<JsonAdaptedJob> jobs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableJobList} with the given jobs.
     */
    @JsonCreator
    public JsonSerializableJobList(@JsonProperty("jobs") List<JsonAdaptedJob> jobs) {
        this.jobs.addAll(jobs);
    }

    /**
     * Converts a given {@code UniqueJobList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableJobList}.
     */
    public JsonSerializableJobList(UniqueJobList source) {
        for (Job job: source) {
            jobs.add(new JsonAdaptedJob(job));
        }
    }

    /**
     * Converts this job list into the model's {@code UniqueJobList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UniqueJobList toModelType() throws IllegalValueException {
        UniqueJobList jobList = new UniqueJobList();
        for (JsonAdaptedJob jsonAdaptedJob : jobs) {
            Job job = jsonAdaptedJob.toModelType();
            if (jobList.containsJob(job)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_JOB);
            }
            jobList.addJob(job);
        }
        return jobList;
    }

}

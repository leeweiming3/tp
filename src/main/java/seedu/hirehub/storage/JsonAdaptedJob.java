package seedu.hirehub.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hirehub.commons.exceptions.IllegalValueException;
import seedu.hirehub.logic.parser.exceptions.ParseException;
import seedu.hirehub.model.job.Job;

/**
 * Jackson-friendly version of {@link Job}.
 */
class JsonAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    private final String title;
    private final String description;
    private final Integer vacancy;

    /**
     * Constructs a {@code JsonAdaptedJob} with the given job details.
     */
    @JsonCreator
    public JsonAdaptedJob(@JsonProperty("title") String title, @JsonProperty("description") String description,
        @JsonProperty("vacancy") Integer vacancy) {
        this.title = title;
        this.description = description;
        this.vacancy = vacancy;
    }

    /**
     * Converts a given {@code Job} into this class for Jackson use.
     */
    public JsonAdaptedJob(Job source) {
        title = source.getTitle();
        description = source.getDescription();
        vacancy = source.getVacancy();
    }

    /**
     * Converts this Jackson-friendly adapted job object into the model's {@code Job} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job.
     */
    public Job toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }
        if (vacancy == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "vacancy"));
        }
        String trimmedTitle = title.trim();
        if (!Job.isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(Job.TITLE_CONSTRAINTS);
        }

        if (vacancy < 0) {
            throw new IllegalValueException(Job.VACANCY_CONSTRAINTS);
        }

        return new Job(title, description, vacancy);
    }
}

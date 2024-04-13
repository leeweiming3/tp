package seedu.hirehub.testutil;

import seedu.hirehub.model.job.Job;

/**
 * A utility class to help with building Job objects.
 */
public class JobBuilder {

    public static final String DEFAULT_TITLE = "SWE";
    public static final String DEFAULT_DESCRIPTION = "High Paying Job";
    public static final int DEFAULT_VACANCY = 3;

    private String title;
    private String description;
    private int vacancy;

    /**
     * Creates a {@code JobBuilder} with the default details.
     */
    public JobBuilder() {
        title = DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        vacancy = DEFAULT_VACANCY;
    }

    /**
     * Initializes the JobBuilder with the data of {@code jobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        title = jobToCopy.getTitle();
        description = jobToCopy.getDescription();
        vacancy = jobToCopy.getVacancy();
    }

    /**
     * Sets the {@code Title} of the {@code Job} that we are building.
     */
    public JobBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Job} that we are building.
     */
    public JobBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set the {@code vacancy} to the {@code Job} that we are building.
     */
    public JobBuilder withVacancy(int vacancy) {
        this.vacancy = vacancy;
        return this;
    }

    public Job build() {
        return new Job(title, description, vacancy);
    }
}

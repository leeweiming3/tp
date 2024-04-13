package seedu.hirehub.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.job.UniqueJobList;

/**
 * A utility class containing a list of {@code Job} objects to be used in tests.
 */
public class TypicalJobs {

    public static final Job QUANTITATIVE_RESEARCHER = new JobBuilder().withTitle("Quantitative Researcher")
            .withDescription("Seeking for candidates strong in mathematics and statistics.").withVacancy(5).build();
    public static final Job OFF_CYCLE = new JobBuilder().withTitle("Software Engineer (Off-Cycle)")
            .withDescription("Off-cycle full stack engineer.").withVacancy(5).build();
    public static final Job BACK_END = new JobBuilder().withTitle("Software Engineer (Back-end)")
            .withDescription("Looking for backend developer. Need to be familiar with computer networks specifically.")
            .withVacancy(1).build();
    public static final Job FRONT_END = new JobBuilder().withTitle("Software Engineer (Front-End)")
            .withDescription("Looking for frontend developer who can deal with the Ui of the app.").build();
    public static final Job QUANTITATIVE_TRADER = new JobBuilder().withTitle("Quantitative Trader")
            .withDescription("There are 5 rounds in total and the final round will be in-person on Hong Kong")
            .withVacancy(10).build();

    private TypicalJobs() {} // prevents instantiation

    /**
     * Returns an {@code UniqueJobList} with all the typical jobs.
     */
    public static UniqueJobList getTypicalJobList() {
        UniqueJobList jobList = new UniqueJobList();
        for (Job job : getTypicalJobs()) {
            jobList.addJob(job);
        }
        return jobList;
    }

    public static List<Job> getTypicalJobs() {
        return new ArrayList<>(Arrays.asList(BACK_END, FRONT_END, OFF_CYCLE, QUANTITATIVE_RESEARCHER,
                QUANTITATIVE_TRADER));
    }
}


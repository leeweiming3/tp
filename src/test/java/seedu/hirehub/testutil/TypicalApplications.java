package seedu.hirehub.testutil;

import static seedu.hirehub.testutil.TypicalJobs.BACK_END;
import static seedu.hirehub.testutil.TypicalJobs.FRONT_END;
import static seedu.hirehub.testutil.TypicalJobs.QUANTITATIVE_RESEARCHER;
import static seedu.hirehub.testutil.TypicalJobs.QUANTITATIVE_TRADER;
import static seedu.hirehub.testutil.TypicalPersons.ALICE;
import static seedu.hirehub.testutil.TypicalPersons.BENSON;
import static seedu.hirehub.testutil.TypicalPersons.CARL;
import static seedu.hirehub.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.status.Status;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalApplications {

    public static final Application ALICE_BACK_END = new ApplicationBuilder().withPerson(ALICE)
            .withJob(BACK_END).build();
    public static final Application BENSON_BACK_END = new ApplicationBuilder().withPerson(BENSON)
            .withJob(BACK_END).withStatus(new Status("OFFERED")).build();
    public static final Application CARL_FRONT_END = new ApplicationBuilder().withPerson(CARL)
            .withJob(FRONT_END).withStatus(new Status("WAITLIST")).build();
    public static final Application DANIEL_QUANTITATIVE_TRADER = new ApplicationBuilder().withPerson(DANIEL)
            .withJob(QUANTITATIVE_TRADER).withStatus(new Status("IN_PROGRESS")).build();
    public static final Application DANIEL_QUANTITATIVE_RESEARCHER = new ApplicationBuilder().withPerson(DANIEL)
            .withJob(QUANTITATIVE_RESEARCHER).withStatus(new Status("OFFERED")).build();

    private TypicalApplications() {} // prevents instantiation

    /**
     * Returns an {@code UniqueApplicationList} with all the typical applications.
     */
    public static UniqueApplicationList getTypicalApplicationList() {
        UniqueApplicationList applicationList = new UniqueApplicationList();
        for (Application application : getTypicalApplications()) {
            applicationList.addApplication(application);
        }
        return applicationList;
    }

    public static List<Application> getTypicalApplications() {
        return new ArrayList<>(Arrays.asList(ALICE_BACK_END, BENSON_BACK_END, CARL_FRONT_END,
                DANIEL_QUANTITATIVE_TRADER, DANIEL_QUANTITATIVE_RESEARCHER));
    }
}

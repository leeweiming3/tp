package seedu.hirehub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hirehub.logic.Messages.MESSAGE_JOBS_LISTED_OVERVIEW;
import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hirehub.testutil.TypicalJobs.BACK_END;
import static seedu.hirehub.testutil.TypicalJobs.FRONT_END;
import static seedu.hirehub.testutil.TypicalJobs.OFF_CYCLE;
import static seedu.hirehub.testutil.TypicalJobs.QUANTITATIVE_RESEARCHER;
import static seedu.hirehub.testutil.TypicalJobs.QUANTITATIVE_TRADER;
import static seedu.hirehub.testutil.TypicalJobs.getTypicalJobList;
import static seedu.hirehub.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.hirehub.model.Model;
import seedu.hirehub.model.ModelManager;
import seedu.hirehub.model.UserPrefs;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.SearchPredicate;
import seedu.hirehub.testutil.JobBuilder;
import seedu.hirehub.testutil.SearchJobPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchJobCommand}.
 */
public class SearchJobCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalJobList(), new UserPrefs(),
            new UniqueApplicationList());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalJobList(), new UserPrefs(),
            new UniqueApplicationList());

    @Test
    public void equals() {
        SearchPredicate<Job> firstDescriptor =
                new SearchJobPredicateBuilder().withTitle("firstTitle").build();
        SearchPredicate<Job> secondDescriptor =
                new SearchJobPredicateBuilder().withTitle("secondTitle").build();

        SearchJobCommand searchJobFirstCommand = new SearchJobCommand(firstDescriptor);
        SearchJobCommand searchJobSecondCommand = new SearchJobCommand(secondDescriptor);

        // same object -> returns true
        assertTrue(searchJobFirstCommand.equals(searchJobFirstCommand));

        // different types -> returns false
        assertFalse(searchJobFirstCommand.equals(1));
        assertFalse(searchJobFirstCommand.equals("Random String"));

        // null -> returns false
        assertFalse(searchJobSecondCommand.equals(null));

        // different Job Predicates -> returns false
        assertFalse(searchJobFirstCommand.equals(searchJobSecondCommand));
    }

    @Test
    public void execute_noJobFound() {
        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> jobDescriptor =
                new SearchJobPredicateBuilder().withTitle("keyword").build();

        SearchJobCommand command = new SearchJobCommand(jobDescriptor);
        expectedModel.updateFilteredJobList(x -> jobDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());
    }

    /**
     * Test search_job method for job title for both exact matching and substring matching
     */
    @Test
    public void execute_matchTitle() {

        Job testJob = new JobBuilder().withTitle("Software Engineer (London)").build();

        expectedModel.addJob(testJob);

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 4);
        SearchPredicate<Job> firstTitleDescriptor = new SearchJobPredicateBuilder()
                .withTitle("Software Engineer").build();
        SearchJobCommand command = new SearchJobCommand(firstTitleDescriptor);
        expectedModel.updateFilteredJobList(x -> firstTitleDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BACK_END, FRONT_END, OFF_CYCLE, testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 1);
        SearchPredicate<Job> secondTitleDescriptor = new SearchJobPredicateBuilder()
                .withTitle("Software Engineer (London)").build();
        command = new SearchJobCommand(secondTitleDescriptor);
        expectedModel.updateFilteredJobList(x -> secondTitleDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> thirdTitleDescriptor = new SearchJobPredicateBuilder()
                .withTitle("Quantitative").build();
        command = new SearchJobCommand(thirdTitleDescriptor);
        expectedModel.updateFilteredJobList(x -> thirdTitleDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(QUANTITATIVE_RESEARCHER, QUANTITATIVE_TRADER),
                model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 6);
        SearchPredicate<Job> fourthTitleDescriptor = new SearchJobPredicateBuilder()
                .withTitle(" ").build();
        command = new SearchJobCommand(fourthTitleDescriptor);
        expectedModel.updateFilteredJobList(x -> fourthTitleDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BACK_END, FRONT_END, OFF_CYCLE, QUANTITATIVE_RESEARCHER,
                QUANTITATIVE_TRADER, testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> fifthTitleDescriptor = new SearchJobPredicateBuilder()
                .withTitle("SoftWare EngiNeer").build();
        command = new SearchJobCommand(fifthTitleDescriptor);
        expectedModel.updateFilteredJobList(x -> fifthTitleDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> sixthTitleDescriptor = new SearchJobPredicateBuilder()
                .withTitle("   ").build();
        command = new SearchJobCommand(sixthTitleDescriptor);
        expectedModel.updateFilteredJobList(x -> sixthTitleDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());
    }

    /**
     * Test search_job method for description for both exact matching and substring matching
     */
    @Test
    public void execute_matchDescription() {

        Job testJob = new JobBuilder().withDescription("Seeking for international olympiad medalists").build();

        expectedModel.addJob(testJob);

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> firstDescriptionPredicate = new SearchJobPredicateBuilder()
                .withDescription("Seeking for").build();
        SearchJobCommand command = new SearchJobCommand(firstDescriptionPredicate);
        expectedModel.updateFilteredJobList(x -> firstDescriptionPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(QUANTITATIVE_RESEARCHER, testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 1);
        SearchPredicate<Job> secondDescriptionPredicate = new SearchJobPredicateBuilder()
                .withDescription("Seeking for candidates strong in mathematics and statistics.").build();
        command = new SearchJobCommand(secondDescriptionPredicate);
        expectedModel.updateFilteredJobList(x -> secondDescriptionPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(QUANTITATIVE_RESEARCHER), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> thirdDescriptionPredicate = new SearchJobPredicateBuilder()
                .withDescription("developer").build();
        command = new SearchJobCommand(thirdDescriptionPredicate);
        expectedModel.updateFilteredJobList(x -> thirdDescriptionPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BACK_END, FRONT_END),
                model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 6);
        SearchPredicate<Job> fourthDescriptionPredicate = new SearchJobPredicateBuilder()
                .withDescription(" ").build();
        command = new SearchJobCommand(fourthDescriptionPredicate);
        expectedModel.updateFilteredJobList(x -> fourthDescriptionPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BACK_END, FRONT_END, OFF_CYCLE, QUANTITATIVE_RESEARCHER,
                QUANTITATIVE_TRADER, testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> fifthDescriptionPredicate = new SearchJobPredicateBuilder()
                .withDescription("LooKing").build();
        command = new SearchJobCommand(fifthDescriptionPredicate);
        expectedModel.updateFilteredJobList(x -> fifthDescriptionPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> sixthDescriptionPredicate = new SearchJobPredicateBuilder()
                .withDescription("   ").build();
        command = new SearchJobCommand(sixthDescriptionPredicate);
        expectedModel.updateFilteredJobList(x -> sixthDescriptionPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());
    }

    /**
     * Test search_job method for vacancy
     */
    @Test
    public void execute_matchVacancy() {

        Job testJob = new JobBuilder().withVacancy(3).build();

        expectedModel.addJob(testJob);

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> firstVacancyPredicate = new SearchJobPredicateBuilder()
                .withVacancy(3).build();
        SearchJobCommand command = new SearchJobCommand(firstVacancyPredicate);
        expectedModel.updateFilteredJobList(x -> firstVacancyPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FRONT_END, testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> secondVacancyPredicate = new SearchJobPredicateBuilder()
                .withVacancy(5).build();
        command = new SearchJobCommand(secondVacancyPredicate);
        expectedModel.updateFilteredJobList(x -> secondVacancyPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(OFF_CYCLE, QUANTITATIVE_RESEARCHER),
                model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> thirdVacancyPredicate = new SearchJobPredicateBuilder()
                .withVacancy(0).build();
        command = new SearchJobCommand(thirdVacancyPredicate);
        expectedModel.updateFilteredJobList(x -> thirdVacancyPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> fourthVacancyPredicate = new SearchJobPredicateBuilder()
                .withVacancy(-1).build();
        command = new SearchJobCommand(thirdVacancyPredicate);
        expectedModel.updateFilteredJobList(x -> fourthVacancyPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());
    }

    /**
     * Test for search_job method given multiple searching criteria
     */
    @Test
    public void execute_matchMultipleFields() {

        Job testJob = new JobBuilder().withVacancy(1).build();

        expectedModel.addJob(testJob);

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> firstMultipleFieldsPredicate = new SearchJobPredicateBuilder()
                .withTitle("Software").withDescription("Looking for").build();
        SearchJobCommand command = new SearchJobCommand(firstMultipleFieldsPredicate);
        expectedModel.updateFilteredJobList(x -> firstMultipleFieldsPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BACK_END, FRONT_END), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> secondMultipleFieldsPredicate = new SearchJobPredicateBuilder()
                .withTitle("SoftWare Engineer").withDescription("Looking for").build();
        command = new SearchJobCommand(secondMultipleFieldsPredicate);
        expectedModel.updateFilteredJobList(x -> secondMultipleFieldsPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 1);
        SearchPredicate<Job> thirdMultipleFieldsPredicate = new SearchJobPredicateBuilder()
                .withTitle("Off-Cycle").withDescription("Off-cycle full stack").withVacancy(5).build();
        command = new SearchJobCommand(thirdMultipleFieldsPredicate);
        expectedModel.updateFilteredJobList(x -> thirdMultipleFieldsPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(OFF_CYCLE), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 2);
        SearchPredicate<Job> fourthMultipleFieldsPredicate = new SearchJobPredicateBuilder()
                .withTitle("S").withVacancy(1).build();
        command = new SearchJobCommand(fourthMultipleFieldsPredicate);
        expectedModel.updateFilteredJobList(x -> fourthMultipleFieldsPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BACK_END, testJob), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> fifthMultipleFieldsPredicate = new SearchJobPredicateBuilder()
                .withTitle("Software Engineer").withDescription("Looking for").withVacancy(0).build();
        command = new SearchJobCommand(fifthMultipleFieldsPredicate);
        expectedModel.updateFilteredJobList(x -> fifthMultipleFieldsPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());

        expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        SearchPredicate<Job> sixthMultipleFieldsPredicate = new SearchJobPredicateBuilder()
                .withTitle("S").withDescription("IOI Gold").withVacancy(1).build();
        command = new SearchJobCommand(sixthMultipleFieldsPredicate);
        expectedModel.updateFilteredJobList(x -> sixthMultipleFieldsPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredJobList());
    }
}

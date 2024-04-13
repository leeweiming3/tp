package seedu.hirehub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hirehub.logic.Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW;
import static seedu.hirehub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hirehub.testutil.TypicalApplications.ALICE_BACK_END;
import static seedu.hirehub.testutil.TypicalApplications.BENSON_BACK_END;
import static seedu.hirehub.testutil.TypicalApplications.CARL_FRONT_END;
import static seedu.hirehub.testutil.TypicalApplications.DANIEL_QUANTITATIVE_RESEARCHER;
import static seedu.hirehub.testutil.TypicalApplications.DANIEL_QUANTITATIVE_TRADER;
import static seedu.hirehub.testutil.TypicalApplications.getTypicalApplicationList;
import static seedu.hirehub.testutil.TypicalJobs.BACK_END;
import static seedu.hirehub.testutil.TypicalJobs.FRONT_END;
import static seedu.hirehub.testutil.TypicalJobs.OFF_CYCLE;
import static seedu.hirehub.testutil.TypicalJobs.QUANTITATIVE_RESEARCHER;
import static seedu.hirehub.testutil.TypicalJobs.getTypicalJobList;
import static seedu.hirehub.testutil.TypicalPersons.ALICE;
import static seedu.hirehub.testutil.TypicalPersons.CARL;
import static seedu.hirehub.testutil.TypicalPersons.DANIEL;
import static seedu.hirehub.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.hirehub.model.Model;
import seedu.hirehub.model.ModelManager;
import seedu.hirehub.model.UserPrefs;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.person.SearchPredicate;
import seedu.hirehub.model.status.Status;
import seedu.hirehub.testutil.ApplicationBuilder;
import seedu.hirehub.testutil.JobBuilder;
import seedu.hirehub.testutil.PersonBuilder;
import seedu.hirehub.testutil.SearchApplicationPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchApplicationCommand}.
 */
public class SearchApplicationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalJobList(), new UserPrefs(),
            getTypicalApplicationList());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalJobList(), new UserPrefs(),
            getTypicalApplicationList());

    @Test
    public void equals() {
        SearchPredicate<Application> firstDescriptor =
                new SearchApplicationPredicateBuilder(ALICE_BACK_END).build();
        SearchPredicate<Application> secondDescriptor =
                new SearchApplicationPredicateBuilder(BENSON_BACK_END).build();

        SearchApplicationCommand searchApplicationFirstCommand = new SearchApplicationCommand(firstDescriptor);
        SearchApplicationCommand searchApplicationSecondCommand = new SearchApplicationCommand(secondDescriptor);

        // same object -> returns true
        assertTrue(searchApplicationFirstCommand.equals(searchApplicationFirstCommand));

        // different types -> returns false
        assertFalse(searchApplicationFirstCommand.equals(10));
        assertFalse(searchApplicationFirstCommand.equals("String"));

        // null -> returns false
        assertFalse(searchApplicationSecondCommand.equals(null));

        // different Application Predicates -> returns false
        assertFalse(searchApplicationFirstCommand.equals(searchApplicationSecondCommand));
    }

    @Test
    public void execute_noApplicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        SearchPredicate<Application> applicationDescriptor =
                new SearchApplicationPredicateBuilder().withJob(OFF_CYCLE).build();

        SearchApplicationCommand command = new SearchApplicationCommand(applicationDescriptor);
        expectedModel.updateFilteredApplicationList(x -> applicationDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());
    }

    /**
     * Test search_app method for searching a matching job in an uniqueFilteredApplicationList
     */
    @Test
    public void execute_matchJob() {

        Job nonExistingJob = new JobBuilder().withTitle("Senior Data Analyst").build();

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        SearchPredicate<Application> firstJobDescriptor = new SearchApplicationPredicateBuilder()
                .withJob(QUANTITATIVE_RESEARCHER).build();
        SearchApplicationCommand command = new SearchApplicationCommand(firstJobDescriptor);
        expectedModel.updateFilteredApplicationList(x -> firstJobDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(DANIEL_QUANTITATIVE_RESEARCHER),
                model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        SearchPredicate<Application> secondJobDescriptor = new SearchApplicationPredicateBuilder()
                .withJob(OFF_CYCLE).build();
        command = new SearchApplicationCommand(secondJobDescriptor);
        expectedModel.updateFilteredApplicationList(x -> secondJobDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        SearchPredicate<Application> thirdJobDescriptor = new SearchApplicationPredicateBuilder()
                .withJob(nonExistingJob).build();
        command = new SearchApplicationCommand(thirdJobDescriptor);
        expectedModel.updateFilteredApplicationList(x -> thirdJobDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());
    }

    /**
     * Test search_app method for searching a matching person in an uniqueFilteredApplicationList
     */
    @Test
    public void execute_matchPerson() {

        Person nonExistingPerson = new PersonBuilder().withEmail("acekhoon@gmail.com").build();

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 2);
        SearchPredicate<Application> firstPersonDescriptor = new SearchApplicationPredicateBuilder()
                .withPerson(DANIEL).build();
        SearchApplicationCommand command = new SearchApplicationCommand(firstPersonDescriptor);
        expectedModel.updateFilteredApplicationList(x -> firstPersonDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL_QUANTITATIVE_TRADER, DANIEL_QUANTITATIVE_RESEARCHER),
                model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        SearchPredicate<Application> secondPersonDescriptor = new SearchApplicationPredicateBuilder()
                .withPerson(ALICE).build();
        command = new SearchApplicationCommand(secondPersonDescriptor);
        expectedModel.updateFilteredApplicationList(x -> secondPersonDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE_BACK_END), model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        SearchPredicate<Application> thirdPersonDescriptor = new SearchApplicationPredicateBuilder()
                .withPerson(nonExistingPerson).build();
        command = new SearchApplicationCommand(thirdPersonDescriptor);
        expectedModel.updateFilteredApplicationList(x -> thirdPersonDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());
    }

    /**
     * Test search_app method for searching a matching status in an uniqueFilteredApplicationList
     */
    @Test
    public void execute_matchStatus() {

        Status nonExistingStatus = new Status("REJECTED");

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 2);
        SearchPredicate<Application> firstStatusDescriptor = new SearchApplicationPredicateBuilder()
                .withStatus(new Status("OFFERED")).build();
        SearchApplicationCommand command = new SearchApplicationCommand(firstStatusDescriptor);
        expectedModel.updateFilteredApplicationList(x -> firstStatusDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON_BACK_END, DANIEL_QUANTITATIVE_RESEARCHER),
                model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        SearchPredicate<Application> secondStatusDescriptor = new SearchApplicationPredicateBuilder()
                .withStatus(nonExistingStatus).build();
        command = new SearchApplicationCommand(secondStatusDescriptor);
        expectedModel.updateFilteredApplicationList(x -> secondStatusDescriptor.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());
    }

    /**
     * Test for search_app method given multiple searching criteria
     */
    @Test
    public void execute_matchMultipleFields() {

        Application testApplication = new ApplicationBuilder().withStatus(new Status("OFFERED"))
                .withJob(QUANTITATIVE_RESEARCHER).build();

        expectedModel.addApplication(testApplication);

        model = expectedModel;

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        SearchPredicate<Application> firstMultipleFieldPredicate = new SearchApplicationPredicateBuilder()
                .withPerson(CARL).withJob(FRONT_END).withStatus(new Status("WAITLIST")).build();
        SearchApplicationCommand command = new SearchApplicationCommand(firstMultipleFieldPredicate);
        expectedModel.updateFilteredApplicationList(x -> firstMultipleFieldPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL_FRONT_END), model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        SearchPredicate<Application> secondMultipleFieldPredicate = new SearchApplicationPredicateBuilder()
                .withPerson(ALICE).withJob(FRONT_END).withStatus(new Status("OFFERED")).build();
        command = new SearchApplicationCommand(secondMultipleFieldPredicate);
        expectedModel.updateFilteredApplicationList(x -> secondMultipleFieldPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        SearchPredicate<Application> thirdMultipleFieldPredicate = new SearchApplicationPredicateBuilder()
                .withPerson(DANIEL).withStatus(new Status("IN_PROGRESS")).build();
        command = new SearchApplicationCommand(thirdMultipleFieldPredicate);
        expectedModel.updateFilteredApplicationList(x -> thirdMultipleFieldPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(DANIEL_QUANTITATIVE_TRADER), model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        SearchPredicate<Application> fourthMultipleFieldPredicate = new SearchApplicationPredicateBuilder()
                .withPerson(ALICE).withJob(BACK_END).build();
        command = new SearchApplicationCommand(fourthMultipleFieldPredicate);
        expectedModel.updateFilteredApplicationList(x -> fourthMultipleFieldPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE_BACK_END),
                model.getFilteredApplicationList());

        expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 2);
        SearchPredicate<Application> fifthMultipleFieldPredicate = new SearchApplicationPredicateBuilder()
                .withJob(QUANTITATIVE_RESEARCHER).withStatus(new Status("OFFERED")).build();
        command = new SearchApplicationCommand(fifthMultipleFieldPredicate);
        expectedModel.updateFilteredApplicationList(x -> fifthMultipleFieldPredicate.test(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL_QUANTITATIVE_RESEARCHER, testApplication),
                model.getFilteredApplicationList());
    }
}

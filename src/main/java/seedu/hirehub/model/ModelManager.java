package seedu.hirehub.model;

import static java.util.Objects.requireNonNull;
import static seedu.hirehub.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.hirehub.commons.core.GuiSettings;
import seedu.hirehub.commons.core.LogsCenter;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.job.UniqueJobList;
import seedu.hirehub.model.person.Person;
import seedu.hirehub.model.status.Status;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    private Optional<Person> lastMentionedPerson;

    private final UniqueJobList jobList;
    private final FilteredList<Job> filteredJobs;
    private Optional<Job> lastMentionedJob;
    private Optional<Application> lastMentionedApplication;

    private final UniqueApplicationList applicationList;
    private final FilteredList<Application> filteredApplications;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs, and jobList.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UniqueJobList jobList, ReadOnlyUserPrefs userPrefs,
                        UniqueApplicationList applicationList) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        lastMentionedPerson = Optional.<Person>empty();
        this.jobList = jobList;
        filteredJobs = new FilteredList<>(jobList.asUnmodifiableObservableList());
        lastMentionedJob = Optional.<Job>empty();
        this.applicationList = applicationList;
        lastMentionedApplication = Optional.<Application>empty();
        filteredApplications = new FilteredList<>(applicationList.asUnmodifiableObservableList());
    }

    public ModelManager() {
        this(new AddressBook(), new UniqueJobList(), new UserPrefs(), new UniqueApplicationList());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getJobsFilePath() {
        return userPrefs.getJobsFilePath();
    }

    @Override
    public void setJobsFilePath(Path jobsFilePath) {
        requireNonNull(jobsFilePath);
        userPrefs.setAddressBookFilePath(jobsFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void setLastMentionedPerson(Person p) {
        lastMentionedPerson = Optional.of(p);
    }

    @Override
    public Optional<Person> getLastMentionedPerson() {
        return lastMentionedPerson;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== JobList ================================================================================

    @Override
    public UniqueJobList getJobList() {
        return this.jobList;
    }

    @Override
    public boolean hasJob(Job job) {
        requireNonNull(job);
        return jobList.containsJob(job);
    }

    @Override
    public void deleteJob(Job target) {
        jobList.removeJob(target);
    }

    @Override
    public void addJob(Job job) {
        jobList.addJob(job);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
    }

    @Override
    public void setJob(Job target, Job editedJob) {
        requireAllNonNull(target, editedJob);
        jobList.setJob(target, editedJob);
    }

    @Override
    public void setLastMentionedJob(Job job) {
        lastMentionedJob = Optional.of(job);
    }

    @Override
    public Optional<Job> getLastMentionedJob() {
        return lastMentionedJob;
    }

    //=========== Filtered Job List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Job} backed by the internal list of
     * {@code UniqueJobList}
     */
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return filteredJobs;
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    //=========== ApplicationList =========================================================================

    @Override
    public boolean hasApplication(Application application) {
        requireNonNull(application);
        return applicationList.containsApplication(application);
    }

    @Override
    public void deleteApplication(Application target) {
        applicationList.removeApplication(target);
    }

    @Override
    public void addApplication(Application application) {
        applicationList.addApplication(application);
        updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
    }

    @Override
    public void setApplication(Application target, Application editedApplication) {
        requireAllNonNull(target, editedApplication);
        applicationList.setApplication(target, editedApplication);
    }

    public UniqueApplicationList getApplicationList() {
        return applicationList;
    }

    /* Updates all applications in application list with current person to new person */
    @Override
    public void replaceApplications(Person target, Person editedPerson) {
        List<Application> applications = new ArrayList<Application>();
        for (Application app : applicationList) {
            if (app.getPerson().equals(target)) {
                applications.add(new Application(editedPerson, app.getJob(), app.getStatus()));
            } else {
                applications.add(app);
            }
        }
        applicationList.setApplications(applications);
    }

    /* Updates all applications in application list with current job to new job */
    @Override
    public void replaceApplications(Job target, Job editedJob) {
        List<Application> applications = new ArrayList<Application>();
        for (Application app: applicationList) {
            if (app.getJob().equals(target)) {
                applications.add(new Application(app.getPerson(), editedJob, app.getStatus()));
            } else {
                applications.add(app);
            }
        }
        applicationList.setApplications(applications);
    }

    @Override
    public int countVacancy(Job jobToFind) {
        for (Job job : jobList) {
            if (job.isSameJob(jobToFind)) {
                return job.getVacancy();
            }
        }
        return 0;
    }

    @Override
    public int countAccepted(Job jobToFind) {
        int countAccepted = 0;
        for (Application app : applicationList) {
            if (app.getJob().isSameJob(jobToFind) && app.getStatus().equals(new Status("OFFERED"))) {
                countAccepted += 1;
            }
        }
        return countAccepted;
    }

    @Override
    public int countRemainingVacancy(String jobTitle) {
        Job jobToFind = new Job(jobTitle, "", 1);
        int countVacancy = countVacancy(jobToFind);
        int countAccepted = countAccepted(jobToFind);
        return countVacancy - countAccepted;
    }

    /* Removes all applications in application list with target person */
    @Override
    public void removeApplications(Person target) {
        List<Application> applications = new ArrayList<Application>();
        for (Application app: applicationList) {
            if (!app.getPerson().equals(target)) {
                applications.add(app);
            }
        }
        applicationList.setApplications(applications);
    }

    /* Removes all applications in application list with target job */
    @Override
    public void removeApplications(Job target) {
        List<Application> applications = new ArrayList<Application>();
        for (Application app: applicationList) {
            if (!app.getJob().equals(target)) {
                applications.add(app);
            }
        }
        applicationList.setApplications(applications);
    }

    /* Clears all applications in the model */
    @Override
    public void clearApplications() {
        applicationList.setApplications(new UniqueApplicationList());
    }

    //=========== Filtered Application List Accessors ======================================================

    /**
     * Returns an unmodifiable view of the list of {@code Application} backed by the internal list of
     * {@code UniqueApplicationList}
     */
    @Override
    public ObservableList<Application> getFilteredApplicationList() {
        return filteredApplications;
    }

    @Override
    public void setLastMentionedApplication(Application p) {
        lastMentionedApplication = Optional.of(p);
    }

    @Override
    public Optional<Application> getLastMentionedApplication() {
        return lastMentionedApplication;
    }

    @Override
    public void updateFilteredApplicationList(Predicate<Application> predicate) {
        requireNonNull(predicate);
        filteredApplications.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && applicationList.equals((otherModelManager.applicationList))
                && jobList.equals((otherModelManager.jobList));
    }
}

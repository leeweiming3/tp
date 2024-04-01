package seedu.hirehub;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.hirehub.commons.core.Config;
import seedu.hirehub.commons.core.LogsCenter;
import seedu.hirehub.commons.core.Version;
import seedu.hirehub.commons.exceptions.DataLoadingException;
import seedu.hirehub.commons.util.ConfigUtil;
import seedu.hirehub.commons.util.StringUtil;
import seedu.hirehub.logic.Logic;
import seedu.hirehub.logic.LogicManager;
import seedu.hirehub.model.AddressBook;
import seedu.hirehub.model.Model;
import seedu.hirehub.model.ModelManager;
import seedu.hirehub.model.ReadOnlyAddressBook;
import seedu.hirehub.model.ReadOnlyUserPrefs;
import seedu.hirehub.model.UserPrefs;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.UniqueJobList;
import seedu.hirehub.model.util.SampleDataUtil;
import seedu.hirehub.storage.AddressBookStorage;
import seedu.hirehub.storage.ApplicationStorage;
import seedu.hirehub.storage.JobsStorage;
import seedu.hirehub.storage.JsonAddressBookStorage;
import seedu.hirehub.storage.JsonApplicationStorage;
import seedu.hirehub.storage.JsonJobsStorage;
import seedu.hirehub.storage.JsonUserPrefsStorage;
import seedu.hirehub.storage.Storage;
import seedu.hirehub.storage.StorageManager;
import seedu.hirehub.storage.UserPrefsStorage;
import seedu.hirehub.ui.Ui;
import seedu.hirehub.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 2, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        JobsStorage jobsStorage = new JsonJobsStorage(userPrefs.getJobsFilePath());
        ApplicationStorage applicationStorage = new JsonApplicationStorage(userPrefs.getApplicationsFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, jobsStorage, applicationStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with data
     * from {@code storage}'s address book and jobs list, {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     * The data from the sample jobs list will be used instead if {@code storage}'s jobs list is not found,
     * or an empty jobs list will be used instead if errors occur when reading {@code storage}'s jobs list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        Optional<UniqueJobList> jobListOptional;
        UniqueJobList initialJobs;
        try {
            jobListOptional = storage.readJobList();
            if (!jobListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getJobsFilePath()
                    + " populated with a sample jobs list.");
            }
            initialJobs = jobListOptional.orElseGet(SampleDataUtil::getSampleUniqueJobList);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getJobsFilePath() + " could not be loaded."
                + " Will be starting with an empty jobs list.");
            initialJobs = new UniqueJobList();
        }

        Optional<UniqueApplicationList> applicationListOptional;
        UniqueApplicationList initialApplications;
        try {
            applicationListOptional = storage.readApplicationList(initialJobs, initialData);
            if (!applicationListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getApplicationFilePath()
                    + " populated with a sample application list.");
            }
            //initialApplications = applicationListOptional.orElseGet(SampleDataUtil::get);
            initialApplications = applicationListOptional.orElseGet(UniqueApplicationList::new);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getApplicationFilePath() + " could not be loaded."
                + " Will be starting with an empty jobs list.");
            initialApplications = new UniqueApplicationList();
        }
        return new ModelManager(initialData, initialJobs, userPrefs, initialApplications);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}

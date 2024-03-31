package seedu.hirehub.storage;

import seedu.hirehub.commons.core.LogsCenter;
import seedu.hirehub.commons.exceptions.DataLoadingException;
import seedu.hirehub.commons.exceptions.IllegalValueException;
import seedu.hirehub.commons.util.FileUtil;
import seedu.hirehub.commons.util.JsonUtil;
import seedu.hirehub.model.job.UniqueJobList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonJobsStorage implements JobsStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonJobsStorage.class);

    private Path filePath;

    public JsonJobsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getJobsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UniqueJobList> readJobList() throws DataLoadingException {
        return readJobList(filePath);
    }

    /**
     * Similar to {@link #readJobList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<UniqueJobList> readJobList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableJobList> jsonJobList= JsonUtil.readJsonFile(
            filePath, JsonSerializableJobList.class);
        if (!jsonJobList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonJobList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }
    @Override
    public void saveJobList(UniqueJobList jobList) throws IOException {
        saveJobList(jobList, filePath);
    }

    /**
     * Similar to {@link #saveJobList(UniqueJobList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveJobList(UniqueJobList jobList, Path filePath) throws IOException {
        requireNonNull(jobList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableJobList(jobList), filePath);
    }
}

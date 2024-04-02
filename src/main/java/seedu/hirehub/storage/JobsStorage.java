package seedu.hirehub.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hirehub.commons.exceptions.DataLoadingException;
import seedu.hirehub.model.job.UniqueJobList;

/**
 * Represents a storage for {@link seedu.hirehub.model.job.UniqueJobList}.
 */
public interface JobsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getJobsFilePath();

    /**
     * Returns job data as a {@link UniqueJobList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<UniqueJobList> readJobList() throws DataLoadingException;

    /**
     * @see #getJobsFilePath()
     */
    Optional<UniqueJobList> readJobList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link UniqueJobList} to the storage.
     * @param jobList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveJobList(UniqueJobList jobList) throws IOException;

    /**
     * @see #saveJobList(UniqueJobList)
     */
    void saveJobList(UniqueJobList jobList, Path filePath) throws IOException;

}

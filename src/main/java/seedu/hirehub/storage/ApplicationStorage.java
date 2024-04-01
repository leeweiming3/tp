package seedu.hirehub.storage;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hirehub.commons.exceptions.DataLoadingException;
import seedu.hirehub.model.ReadOnlyAddressBook;
import seedu.hirehub.model.application.UniqueApplicationList;
import seedu.hirehub.model.job.UniqueJobList;

/**
 * Represents a storage for {@link seedu.hirehub.model.application.UniqueApplicationList}.
 */
public interface ApplicationStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getApplicationFilePath();

    /**
     * Returns job data as a {@link seedu.hirehub.model.application.UniqueApplicationList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<UniqueApplicationList> readApplicationList(UniqueJobList jobs,
                                                        ReadOnlyAddressBook people) throws DataLoadingException;

    /**
     * @see #getApplicationFilePath()
     */
    Optional<UniqueApplicationList> readApplicationList(UniqueJobList jobs,
                                                        ReadOnlyAddressBook people,
                                                        Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link UniqueApplicationList} to the storage.
     *
     * @param applicationList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveApplicationList(UniqueApplicationList applicationList) throws IOException;

    /**
     * @see #saveApplicationList(UniqueApplicationList)
     */
    void saveApplicationList(UniqueApplicationList applicationList, Path filePath) throws IOException;

}

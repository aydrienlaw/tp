package seedu.orcashbuddy.storage;
import seedu.orcashbuddy.ui.Ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles saving and loading of {@link ExpenseManager} objects to persistent storage.
 * <p>
 * Expenses are serialized to a file named {@code appdata.ser} inside a {@code data} folder.
 * This class ensures that the storage folder and file exist, and provides user-friendly
 * error messages via {@link Ui} in case of exceptions.
 */
public class StorageManager {

    private static final Logger LOGGER = Logger.getLogger(StorageManager.class.getName());
    private static final String DIRECTORY = "data";
    private static final String FILE_NAME = "appdata.ser";

    /**
     * Saves the given {@link ExpenseManager} to disk.
     * <p>
     * If the storage folder does not exist, it will be created. If saving fails,
     * a user-friendly message is displayed via the {@link Ui} object.
     *
     * @param expenseManager the ExpenseManager object to save; must not be null
     * @param ui             the UI object to display error messages; must not be null
     */
    public static void saveExpenseManager(ExpenseManager expenseManager, Ui ui) {
        assert expenseManager != null : "ExpenseManager must not be null";
        assert ui != null : "Ui must not be null";

        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                boolean dirCreated = dir.mkdir();
                if (!dirCreated) {
                    ui.showError("Unable to create storage folder. Your expenses may not be saved.");
                    LOGGER.warning("Failed to create storage folder: " + DIRECTORY);
                    return;
                }
            }

            File file = new File(dir, FILE_NAME);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(expenseManager);
                LOGGER.info("ExpenseManager successfully saved to " + file.getAbsolutePath());
            } catch (IOException e) {
                ui.showError("Failed to save your expenses.");
                LOGGER.log(Level.WARNING, "Failed to save ExpenseManager", e);
            }

        } catch (SecurityException e) {
            ui.showError("Permission denied. Unable to access storage to save expenses.");
            LOGGER.log(Level.WARNING, "Security exception when saving ExpenseManager", e);
        }
    }

    /**
     * Loads the {@link ExpenseManager} from disk.
     * <p>
     * If the storage folder or file does not exist, they will be created.
     * If reading fails or data is corrupted, an empty ExpenseManager is returned
     * and a user-friendly message is displayed via the {@link Ui} object.
     *
     * @param ui the UI object to display error messages; must not be null
     * @return the loaded ExpenseManager object, or a new empty one if loading fails
     */
    public static ExpenseManager loadExpenseManager(Ui ui) {
        assert ui != null : "Ui must not be null";

        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                boolean dirCreated = dir.mkdir();
                if (!dirCreated) {
                    ui.showError("Unable to create storage folder.");
                    LOGGER.warning("Failed to create storage folder: " + DIRECTORY);
                    return new ExpenseManager();
                }
                LOGGER.info("Storage folder created: " + DIRECTORY);
            }

            File file = new File(dir, FILE_NAME);
            if (!file.exists()) {
                try {
                    boolean fileCreated = file.createNewFile();
                    if (!fileCreated) {
                        ui.showError("Unable to create storage folder.");
                        LOGGER.warning("Failed to create storage file: " + file.getAbsolutePath());
                    }
                    else {
                        LOGGER.info("Storage file created: " + file.getAbsolutePath());
                    }
                } catch (IOException e) {
                    ui.showError("An error occurred while creating storage file. Starting fresh.");
                    LOGGER.log(Level.WARNING, "IOException while creating storage file", e);
                } catch (SecurityException e) {
                    ui.showError("Permission denied. Unable to create storage file. Starting fresh.");
                    LOGGER.log(Level.WARNING, "Security exception while creating storage file", e);
                }
                return new ExpenseManager();
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof ExpenseManager) {
                    LOGGER.info("ExpenseManager successfully loaded from " + file.getAbsolutePath());
                    return (ExpenseManager) obj;
                } else {
                    ui.showError("Saved data is corrupted. Starting with empty expenses.");
                    LOGGER.warning("Data in storage file is not an ExpenseManager: " + file.getAbsolutePath());
                    return new ExpenseManager();
                }
            } catch (IOException e) {
                ui.showError("Failed to read saved expenses. Starting fresh.");
                LOGGER.log(Level.WARNING, "IOException while reading storage file", e);
            } catch (ClassNotFoundException e) {
                ui.showError("Saved data is incompatible. Starting with empty expenses.");
                LOGGER.log(Level.WARNING, "ClassNotFoundException while reading storage file", e);
            }

        } catch (SecurityException e) {
            ui.showError("Permission denied. Cannot access saved data. Starting fresh.");
            LOGGER.log(Level.WARNING, "Security exception while accessing storage directory", e);
        }

        return new ExpenseManager();
    }
}
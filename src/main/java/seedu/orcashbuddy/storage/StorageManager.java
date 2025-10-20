package seedu.orcashbuddy.storage;
import seedu.orcashbuddy.ui.Ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StorageManager {

    private static final String DIRECTORY = "data";
    private static final String FILE_NAME = "appdata.ser";

    public static void saveExpenseManager(ExpenseManager expenseManager, Ui ui) {
        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                boolean dirCreated = dir.mkdir();
                if (!dirCreated) {
                    ui.showError("Unable to create storage folder. Your expenses may not be saved.");
                    return;
                }
            }

            File file = new File(dir, FILE_NAME);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(expenseManager);
            } catch (IOException e) {
                ui.showError("Failed to save your expenses.");
            }

        } catch (SecurityException e) {
            ui.showError("Permission denied. Unable to access storage to save expenses.");
        }
    }

    public static ExpenseManager loadExpenseManager(Ui ui) {
        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                boolean dirCreated = dir.mkdir();
                if (!dirCreated) {
                    ui.showError("Unable to create storage folder.");
                    return new ExpenseManager();
                }
            }

            File file = new File(dir, FILE_NAME);
            if (!file.exists()) {
                try {
                    boolean fileCreated = file.createNewFile();
                    if (!fileCreated) {
                        ui.showError("Unable to create storage folder.");
                    }
                } catch (IOException e) {
                    ui.showError("An error occurred while creating storage file. Starting fresh.");
                } catch (SecurityException e) {
                    ui.showError("Permission denied. Unable to create storage file. Starting fresh.");
                }
                return new ExpenseManager();
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof ExpenseManager) {
                    return (ExpenseManager) obj;
                } else {
                    ui.showError("Saved data is corrupted. Starting with empty expenses.");
                    return new ExpenseManager();
                }
            } catch (IOException e) {
                ui.showError("Failed to read saved expenses. Starting fresh.");
            } catch (ClassNotFoundException e) {
                ui.showError("Saved data is incompatible. Starting with empty expenses.");
            }

        } catch (SecurityException e) {
            ui.showError("Permission denied. Cannot access saved data. Starting fresh.");
        }

        // fallback
        return new ExpenseManager();
    }
}
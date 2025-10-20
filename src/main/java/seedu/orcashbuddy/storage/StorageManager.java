package seedu.orcashbuddy.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StorageManager {

    private static final String FILE_NAME = "appdata.ser";
    private static final String DIRECTORY = "data"; // optional folder if you want to save in a folder

    public static void saveExpenseManager(ExpenseManager expenseManager) {
        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdir(); // create folder if not exists
            }

            File file = new File(dir, FILE_NAME);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(expenseManager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExpenseManager loadExpenseManager() {
        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdir(); // create folder if not exists
            }

            File file = new File(dir, FILE_NAME);
            if (!file.exists()) {
                // create empty file so it's ready for saving later
                file.createNewFile();
                return new ExpenseManager(); // return empty manager
            }

            // file exists, load
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ExpenseManager) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            // fallback if loading fails
            return new ExpenseManager();
        }
    }
}
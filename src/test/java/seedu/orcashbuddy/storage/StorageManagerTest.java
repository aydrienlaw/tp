//@@author saheer17
package seedu.orcashbuddy.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.ui.Ui;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the StorageManager class.
 * This class tests the saving and loading functionality of ExpenseManager objects.
 * It includes tests for normal operation, handling missing files, and cleanup after tests.
 */

class MockUi extends Ui { }

public class StorageManagerTest {

    private static final String DIRECTORY = "data";
    private static final String FILE_NAME = "appdata.ser";

    private Ui ui;
    private ExpenseManager manager;

    /**
     * Sets up a new ExpenseManager and MockUi before each test.
     * Also deletes any previous test files or directories to ensure a clean environment.
     */
    @BeforeEach
    void setUp() {
        ui = new MockUi();
        manager = new ExpenseManager();
        // Delete previous test data if exists
        File file = new File(DIRECTORY, FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        File dir = new File(DIRECTORY);
        if (dir.exists()) {
            dir.delete();
        }
    }

    /**
     * Tests that saving and loading an ExpenseManager preserves its data.
     * Verifies total expenses, budget, remaining balance, and individual expense details.
     */
    @Test
    void testSaveAndLoadExpenseManager() {
        // Add an expense
        Expense expense = new Expense(50.0, "Groceries", "Food");
        manager.addExpense(expense);
        manager.setBudget(200.0);

        // Save
        StorageManager.saveExpenseManager(manager, ui);

        // Load
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify data loaded correctly
        assertEquals(manager.getTotalExpenses(), loadedManager.getTotalExpenses());
        assertEquals(manager.getBudget(), loadedManager.getBudget());
        assertEquals(manager.getRemainingBalance(), loadedManager.getRemainingBalance());

        ArrayList<Expense> loadedExpenses = loadedManager.findExpensesByDescription("Groceries");
        assertEquals(1, loadedExpenses.size());
        assertEquals(expense.getAmount(), loadedExpenses.get(0).getAmount());
        assertEquals(expense.getDescription(), loadedExpenses.get(0).getDescription());
        assertEquals(expense.getCategory(), loadedExpenses.get(0).getCategory());
    }

    /**
     * Tests loading when the storage file does not exist.
     * Verifies that an empty ExpenseManager is returned and no exceptions are thrown.
     */
    @Test
    void testLoadWhenFileDoesNotExist() {
        // Ensure no file exists
        File file = new File(DIRECTORY, FILE_NAME);
        if (file.exists()) {
            file.delete();
        }

        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Should return empty ExpenseManager
        assertEquals(0, loadedManager.getSize());
    }

    /**
     * Cleans up any files or directories created during the test.
     * Runs after each test to ensure a clean environment for subsequent tests.
     */
    @AfterEach
    void tearDown() {
        // Clean up test files
        File file = new File(DIRECTORY, FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        File dir = new File(DIRECTORY);
        if (dir.exists()) {
            dir.delete();
        }
    }
}

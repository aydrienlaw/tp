package seedu.orcashbuddy.storage;

import org.junit.jupiter.api.*;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.ui.Ui;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MockUi extends Ui {
    private String lastError;

    @Override
    public void showError(String message) {
        lastError = message;
    }

    public String getLastError() {
        return lastError;
    }
}

public class StorageManagerTest {

    private static final String DIRECTORY = "data";
    private static final String FILE_NAME = "appdata.ser";

    private Ui ui;
    private ExpenseManager manager;

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

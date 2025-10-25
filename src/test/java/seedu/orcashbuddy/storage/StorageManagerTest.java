//@@author saheer17
package seedu.orcashbuddy.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.ui.Ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        cleanupTestFiles();
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

        List<Expense> loadedExpenses = loadedManager.findExpensesByDescription("Groceries");
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
     * Tests saving and loading an empty ExpenseManager.
     * Verifies that empty state is preserved correctly.
     */
    @Test
    void testSaveAndLoadEmptyExpenseManager() {
        // Save empty manager
        StorageManager.saveExpenseManager(manager, ui);

        // Load
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify empty state
        assertEquals(0, loadedManager.getSize());
        assertEquals(0.0, loadedManager.getBudget());
        assertEquals(0.0, loadedManager.getTotalExpenses());
        assertEquals(0.0, loadedManager.getRemainingBalance());
    }

    /**
     * Tests saving and loading multiple expenses.
     * Verifies that all expenses are preserved with correct order and details.
     */
    @Test
    void testSaveAndLoadMultipleExpenses() throws OrCashBuddyException {
        // Add multiple expenses
        Expense expense1 = new Expense(50.0, "Groceries", "Food");
        Expense expense2 = new Expense(100.0, "Electricity", "Utilities");
        Expense expense3 = new Expense(25.50, "Coffee", "Food");

        manager.addExpense(expense1);
        manager.addExpense(expense2);
        manager.addExpense(expense3);
        manager.setBudget(500.0);

        // Mark one expense
        manager.markExpense(2);

        // Save
        StorageManager.saveExpenseManager(manager, ui);

        // Load
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify all expenses loaded
        assertEquals(3, loadedManager.getSize());
        assertEquals(500.0, loadedManager.getBudget());
        assertEquals(100.0, loadedManager.getTotalExpenses()); // Only marked expense counts

        // Verify expense details
        List<Expense> expenses = loadedManager.getExpenses();
        assertEquals(50.0, expenses.get(0).getAmount());
        assertEquals("Groceries", expenses.get(0).getDescription());
        assertFalse(expenses.get(0).isMarked());

        assertEquals(100.0, expenses.get(1).getAmount());
        assertTrue(expenses.get(1).isMarked());

        assertEquals(25.50, expenses.get(2).getAmount());
        assertEquals("Coffee", expenses.get(2).getDescription());
    }

    /**
     * Tests that the storage directory is created if it doesn't exist.
     */
    @Test
    void testDirectoryCreation() {
        // Delete directory if exists
        File dir = new File(DIRECTORY);
        if (dir.exists()) {
            File file = new File(dir, FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
            dir.delete();
        }

        // Save should create directory
        StorageManager.saveExpenseManager(manager, ui);

        // Verify directory exists
        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());
    }

    /**
     * Tests that the storage file is created if it doesn't exist during load.
     */
    @Test
    void testFileCreationDuringLoad() {
        // Ensure directory exists but file doesn't
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, FILE_NAME);
        if (file.exists()) {
            file.delete();
        }

        // Load should handle missing file gracefully
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Should return empty manager
        assertNotNull(loadedManager);
        assertEquals(0, loadedManager.getSize());
    }

    /**
     * Tests loading with corrupted data file.
     * Verifies that corrupted data is handled gracefully.
     */
    @Test
    void testLoadCorruptedFile() throws IOException {
        // Create directory
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Write corrupted data to file
        File file = new File(dir, FILE_NAME);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write("corrupted data that is not serialized".getBytes());
        }

        // Load should handle corrupted file gracefully
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Should return empty manager
        assertNotNull(loadedManager);
        assertEquals(0, loadedManager.getSize());
    }

    /**
     * Tests saving and loading with marked and unmarked expenses.
     * Verifies that marked status is preserved.
     */
    @Test
    void testSaveAndLoadMarkedExpenses() throws OrCashBuddyException {
        // Add expenses
        manager.addExpense(new Expense(100.0, "Rent", "Housing"));
        manager.addExpense(new Expense(50.0, "Food", "Groceries"));
        manager.setBudget(500.0);

        // Mark first expense
        manager.markExpense(1);

        // Save
        StorageManager.saveExpenseManager(manager, ui);

        // Load
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify marked status
        List<Expense> expenses = loadedManager.getExpenses();
        assertTrue(expenses.get(0).isMarked());
        assertFalse(expenses.get(1).isMarked());
        assertEquals(100.0, loadedManager.getTotalExpenses());
    }

    /**
     * Tests that budget calculations are preserved after save and load.
     */
    @Test
    void testBudgetCalculationsPreserved() throws OrCashBuddyException {
        // Set up expenses and budget
        manager.addExpense(new Expense(150.0, "Shopping", "Retail"));
        manager.addExpense(new Expense(75.0, "Dining", "Food"));
        manager.setBudget(300.0);

        // Mark expenses
        manager.markExpense(1);
        manager.markExpense(2);

        double originalTotal = manager.getTotalExpenses();
        double originalRemaining = manager.getRemainingBalance();

        // Save and load
        StorageManager.saveExpenseManager(manager, ui);
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify calculations
        assertEquals(originalTotal, loadedManager.getTotalExpenses());
        assertEquals(originalRemaining, loadedManager.getRemainingBalance());
        assertEquals(300.0, loadedManager.getBudget());
    }

    /**
     * Tests saving multiple times overwrites previous data correctly.
     */
    @Test
    void testMultipleSavesOverwrite() {
        // First save
        manager.addExpense(new Expense(100.0, "First", "Category1"));
        manager.setBudget(200.0);
        StorageManager.saveExpenseManager(manager, ui);

        // Second save with different data
        manager.addExpense(new Expense(50.0, "Second", "Category2"));
        manager.setBudget(300.0);
        StorageManager.saveExpenseManager(manager, ui);

        // Load and verify latest data
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);
        assertEquals(2, loadedManager.getSize());
        assertEquals(300.0, loadedManager.getBudget());
    }

    /**
     * Tests that expense categories are preserved.
     */
    @Test
    void testExpenseCategoriesPreserved() {
        // Add expenses with different categories
        manager.addExpense(new Expense(50.0, "Lunch", "Food"));
        manager.addExpense(new Expense(100.0, "Internet", "Utilities"));
        manager.addExpense(new Expense(25.0, "Movie", "Entertainment"));

        // Save and load
        StorageManager.saveExpenseManager(manager, ui);
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify categories
        List<Expense> foodExpenses = loadedManager.findExpensesByCategory("Food");
        assertEquals(1, foodExpenses.size());

        List<Expense> utilityExpenses = loadedManager.findExpensesByCategory("Utilities");
        assertEquals(1, utilityExpenses.size());

        List<Expense> entertainmentExpenses = loadedManager.findExpensesByCategory("Entertainment");
        assertEquals(1, entertainmentExpenses.size());
    }

    /**
     * Tests that large expense amounts are preserved accurately.
     */
    @Test
    void testLargeExpenseAmounts() throws OrCashBuddyException {
        // Add expense with large amount
        double largeAmount = 999999.99;
        manager.addExpense(new Expense(largeAmount, "Large Purchase", "Major"));
        manager.setBudget(1000000.0);
        manager.markExpense(1);

        // Save and load
        StorageManager.saveExpenseManager(manager, ui);
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify large amount preserved
        List<Expense> expenses = loadedManager.getExpenses();
        assertEquals(largeAmount, expenses.get(0).getAmount(), 0.01);
    }

    /**
     * Tests that expense descriptions with special characters are preserved.
     */
    @Test
    void testSpecialCharactersInDescription() {
        // Add expense with special characters
        manager.addExpense(new Expense(50.0, "Café & Restaurant: 50% off!", "Food"));

        // Save and load
        StorageManager.saveExpenseManager(manager, ui);
        ExpenseManager loadedManager = StorageManager.loadExpenseManager(ui);

        // Verify special characters preserved
        List<Expense> expenses = loadedManager.getExpenses();
        assertEquals("Café & Restaurant: 50% off!", expenses.get(0).getDescription());
    }

    /**
     * Helper method to clean up test files and directories.
     */
    private void cleanupTestFiles() {
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
     * Cleans up any files or directories created during the test.
     * Runs after each test to ensure a clean environment for subsequent tests.
     */
    @AfterEach
    void tearDown() {
        cleanupTestFiles();
    }
}

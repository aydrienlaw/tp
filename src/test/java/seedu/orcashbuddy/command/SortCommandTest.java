//@@author saheer17
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Command-level tests for the SortCommand.
 * Verifies that expenses are sorted in descending order by amount
 * and correctly displayed in the UI.
 */
class SortCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        List<Expense> lastSortedExpenses = null;
        Boolean isListEmpty = false;

        @Override
        public void showSortedExpenseList(List<Expense> expenses) {
            this.lastSortedExpenses = expenses;
        }

        @Override
        public void showEmptyExpenseList() {
            this.isListEmpty = true;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
    }

    /**
     * Tests that SortCommand sorts multiple expenses in descending order of amount.
     */
    @Test
    void execute_withMultipleExpenses_sortsDescendingByAmount() throws Exception {
        // Add expenses in unsorted order
        new AddCommand(12.50, "Lunch").execute(manager, ui);
        new AddCommand(7.25, "Coffee").execute(manager, ui);
        new AddCommand(25.00, "Groceries").execute(manager, ui);

        // Execute the sort command
        new SortCommand().execute(manager, ui);

        // Verify sorted order (highest to lowest)
        assertEquals(3, ui.lastSortedExpenses.size());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Groceries - $25.00",
                ui.lastSortedExpenses.get(0).formatForDisplay());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Lunch - $12.50",
                ui.lastSortedExpenses.get(1).formatForDisplay());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Coffee - $7.25",
                ui.lastSortedExpenses.get(2).formatForDisplay());
    }

    /**
     * Tests that executing the SortCommand when there are no expenses
     * throws an OrCashBuddyException indicating the expense list is empty.
     */
    @Test
    void execute_withNoExpenses_displaysNoExpenseAddedMessage() throws Exception{
        // Execute SortCommand when no expenses exist
        new SortCommand().execute(manager, ui);

        // Verify printed message
        assertTrue(ui.isListEmpty);
    }

    /**
     * Tests that when multiple expenses have the same amount,
     * the {@code SortCommand} preserves their original insertion order.
     * <p>
     * This verifies that the sort operation is stable, meaning that
     * equal elements retain their relative order after sorting.
     * In this case, "Zebra" was added before "Apple", so it appears first
     * even though both have the same amount.
     */
    @Test
    void execute_withEqualAmounts_preservesInsertionOrder() throws Exception {
        new AddCommand(10.00, "Zebra").execute(manager, ui);
        new AddCommand(10.00, "Apple").execute(manager, ui);

        new SortCommand().execute(manager, ui);

        assertEquals(2, ui.lastSortedExpenses.size());
        // Expect the same order they were added, since amounts are equal
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Zebra - $10.00",
                ui.lastSortedExpenses.get(0).formatForDisplay());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Apple - $10.00",
                ui.lastSortedExpenses.get(1).formatForDisplay());
    }
}

//@@author saheer17
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Command-level tests for deleting an expense.
 */
class DeleteCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Expense lastDeletedExpense;
        Double seenTotal = null;
        Double seenBudget= null;
        Double seenRemaining = null;
        ArrayList<Expense> lastListedExpenses;

        @Override
        public void showDeletedExpense(Expense expense) {
            this.lastDeletedExpense = expense;
        }

        @Override
        public void showList(double totalExpense, double budget,
                             double remainingBalance, ArrayList<Expense> expenses) {
            this.seenTotal = totalExpense;
            this.seenBudget = budget;
            this.seenRemaining = remainingBalance;
            this.lastListedExpenses = expenses;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
        // Seed with two expenses
        new AddCommand(5.00, "Snack").execute(manager, ui);
        new AddCommand(10.00, "Taxi").execute(manager, ui);
    }

    @Test
    void execute_validIndex_deletesAndReturnsExpense() throws Exception {
        new DeleteCommand(1).execute(manager, ui);

        assertNotNull(ui.lastDeletedExpense, "Ui.showDeletedExpense should be called");
        assertEquals("Snack", ui.lastDeletedExpense.getDescription());

        // Now list and ensure only 'Taxi' remains
        new ListCommand().execute(manager, ui);
        assertEquals(1, ui.lastListedExpenses.size());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Taxi - $10.00",
                ui.lastListedExpenses.get(0).formatForDisplay());
    }

    @Test
    void execute_invalidIndex_throws() {
        assertThrows(AssertionError.class, () -> new DeleteCommand(3).execute(manager, ui));
        assertThrows(AssertionError.class, () -> new DeleteCommand(0).execute(manager, ui));
    }

    @Test
    void execute_deletePreviouslyMarked_rebalancesTotals() throws Exception {
        // Seed + mark
        new AddCommand(40.00, "Books").execute(manager, ui);
        new SetBudgetCommand(200.00).execute(manager, ui);
        new MarkCommand(3).execute(manager, ui);

        // Totals after mark
        new ListCommand().execute(manager, ui);
        assertEquals(40.00, ui.seenTotal, 1e-6);
        assertEquals(160.00, ui.seenRemaining, 1e-6);

        // Delete the marked expense -> totals drop to zero
        new DeleteCommand(3).execute(manager, ui);
        new ListCommand().execute(manager, ui);
        assertEquals(0.00, ui.seenTotal, 1e-6);
        assertEquals(200.00, ui.seenRemaining, 1e-6);
    }

}

//@@author saheer17
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;
import seedu.orcashbuddy.storage.BudgetStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Command-level tests for deleting an expense.
 * <p>
 * These tests verify that the {@code DeleteCommand} correctly removes expenses,
 * updates totals and remaining budget, and displays appropriate budget statuses
 * under different conditions.
 */
class DeleteCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Expense lastDeletedExpense;
        Double seenTotal = null;
        Double seenBudget= null;
        Double seenRemaining = null;
        List<Expense> lastListedExpenses;
        boolean budgetStatusShown = false;

        @Override
        public void showDeletedExpense(Expense expense) {
            this.lastDeletedExpense = expense;
        }

        @Override
        public void showFinancialSummary(double budget, double totalExpense,
                                         double remainingBalance, List<Expense> expenses) {
            this.seenTotal = totalExpense;
            this.seenBudget = budget;
            this.seenRemaining = remainingBalance;
            this.lastListedExpenses = expenses;
        }

        @Override
        public void showBudgetStatus(BudgetStatus status, double remaining) {
            this.budgetStatusShown = true;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
        new AddCommand(5.00, "Snack").execute(manager, ui);
        new AddCommand(10.00, "Taxi").execute(manager, ui);
    }

    /**
     * Tests that executing {@code DeleteCommand} with a valid index successfully deletes
     * the corresponding expense and that the UI correctly displays the deleted expense.
     * <p>
     * After deletion, the remaining expense list should exclude the deleted item.
     */
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

    /**
     * Tests that deleting an expense previously marked as spent correctly
     * rebalances the total and remaining budget values.
     * <p>
     * This ensures that marked expenses are properly removed from the budget calculations
     * and that subsequent listings reflect the updated financial summary.
     */
    @Test
    void execute_deletePreviouslyMarked_rebalancesTotals() throws Exception {
        new AddCommand(40.00, "Books").execute(manager, ui);
        new SetBudgetCommand(200.00).execute(manager, ui);
        new MarkCommand(3).execute(manager, ui);
        new ListCommand().execute(manager, ui);
        assertEquals(40.00, ui.seenTotal, 1e-6);
        assertEquals(160.00, ui.seenRemaining, 1e-6);
        // Delete the marked expense -> totals drop to zero
        new DeleteCommand(3).execute(manager, ui);
        new ListCommand().execute(manager, ui);
        assertEquals(0.00, ui.seenTotal, 1e-6);
        assertEquals(200.00, ui.seenRemaining, 1e-6);
    }

    /**
     * Tests that deleting an unmarked expense while the budget status is non-OK
     * (e.g., NEAR or EXCEEDED) correctly triggers the UI to display the budget status.
     * <p>
     * This ensures that {@code DeleteCommand} prompts the user about their current
     * budget situation when removing unmarked expenses under constrained budgets.
     */
    @Test
    void execute_deleteUnmarkedWithNonOkBudgetStatus_showsBudgetStatus() throws Exception {
        new AddCommand(3.00, "Coffee").execute(manager, ui);
        new SetBudgetCommand(18.00).execute(manager, ui);
        new MarkCommand(1).execute(manager, ui);
        new MarkCommand(2).execute(manager, ui);
        ui.budgetStatusShown = false;
        // Delete the UNMARKED expense (index 3, Coffee $3.00)
        new DeleteCommand(3).execute(manager, ui);
        // After delete: budget=$18, total=$15, remaining=$3 (still NEAR)
        // This deletes an unmarked expense while status is non-OK
        assertTrue(ui.budgetStatusShown, "Budget status should be displayed");
        assertEquals(3.00, manager.getRemainingBalance(), 1e-6);
        assertEquals(BudgetStatus.NEAR, manager.determineBudgetStatus());
    }

}

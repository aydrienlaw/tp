//@@author aydrienlaw
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
 * Command-level tests for setting the budget.
 */
class SetBudgetCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Double lastBudgetShown = null;
        Double seenTotal = null;
        Double seenBudget = null;
        Double seenRemaining = null;
        ArrayList<Expense> lastListedExpenses = null;

        @Override
        public void showNewBudget(double budget) {
            this.lastBudgetShown = budget;
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
    }

    @Test
    void execute_setsBudget_andListReflectsIt() throws Exception {
        new SetBudgetCommand(150.00).execute(manager, ui);

        assertNotNull(ui.lastBudgetShown);
        assertEquals(150.00, ui.lastBudgetShown, 1e-6);

        // Add an expense and verify remaining
        new AddCommand(30.00, "Groceries").execute(manager, ui);

        new ListCommand().execute(manager, ui);
        assertEquals(0.00, ui.seenTotal, 1e-6);
        assertEquals(150.00, ui.seenBudget, 1e-6);
        assertEquals(150.00, ui.seenRemaining, 1e-6);
        assertEquals(1, ui.lastListedExpenses.size());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Groceries - $30.00",
                ui.lastListedExpenses.get(0).formatForDisplay());
    }

    @Test
    void execute_nonPositiveBudget_asserts() {
        assertThrows(AssertionError.class, () -> new SetBudgetCommand(0.0).execute(manager, ui));
        assertThrows(AssertionError.class, () -> new SetBudgetCommand(-10.0).execute(manager, ui));
    }
}

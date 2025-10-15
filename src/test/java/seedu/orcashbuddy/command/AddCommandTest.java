//@@author limzerui
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
 * Command-level tests for adding an expense.
 * These tests exercise AddCommand#execute(...) against ExpenseManager and a stubbed Ui.
 */
class AddCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Expense lastNewExpense;
        Double seenTotal;
        Double seenBudget;
        Double seenRemaining;
        ArrayList<Expense> lastListedExpenses;

        @Override
        public void showNewExpense(Expense expense) {
            this.lastNewExpense = expense;
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
    void execute_addsExpense_showsInUi() throws Exception {
        AddCommand cmd = new AddCommand(12.50, "Lunch");
        cmd.execute(manager, ui);

        // UI should be notified with the same expense
        assertNotNull(ui.lastNewExpense, "Ui.showNewExpense should be called");
        assertEquals(12.50, ui.lastNewExpense.getAmount(), 1e-6);
        assertEquals("Lunch", ui.lastNewExpense.getDescription());

        // Listing should show exactly one expense with correct formatting
        new ListCommand().execute(manager, ui);
        assertNotNull(ui.lastListedExpenses);
        assertEquals(1, ui.lastListedExpenses.size());
        assertEquals("[ ] Lunch - $12.50", ui.lastListedExpenses.get(0).formatForDisplay());
    }

    @Test
    void execute_zeroAmount_asserts() {
        AddCommand cmd = new AddCommand(0.0, "Invalid expense");
        // This relies on JVM assertions being enabled (-ea)
        assertThrows(AssertionError.class, () -> cmd.execute(manager, ui));
    }

    @Test
    void execute_blankDescription_asserts() {
        AddCommand cmd = new AddCommand(5.0, "   ");
        // This relies on JVM assertions being enabled (-ea)
        assertThrows(AssertionError.class, () -> cmd.execute(manager, ui));
    }
}

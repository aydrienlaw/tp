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
        ArrayList<Expense> lastListedExpenses;

        @Override
        public void showDeletedExpense(Expense expense) {
            this.lastDeletedExpense = expense;
        }

        @Override
        public void showList(double totalExpense, double budget,
                             double remainingBalance, ArrayList<Expense> expenses) {
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
        assertEquals("[ ] Taxi - $10.00", ui.lastListedExpenses.get(0).formatForDisplay());
    }

    @Test
    void execute_invalidIndex_throws() {
        assertThrows(AssertionError.class, () -> new DeleteCommand(3).execute(manager, ui));
        assertThrows(AssertionError.class, () -> new DeleteCommand(0).execute(manager, ui));
    }
}

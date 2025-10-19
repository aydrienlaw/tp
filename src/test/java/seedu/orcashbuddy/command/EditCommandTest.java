//@@author gumingyoujia
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Command-level tests for editing expenses.
 */
class EditCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    /**
     * Stub version of Ui that captures the last expense shown.
     */
    static class StubUi extends Ui {
        Expense lastEditedExpense = null;

        @Override
        public void showEditedExpense(Expense expense) {
            this.lastEditedExpense = expense;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();

        new AddCommand(12.50, "Lunch", "Food").execute(manager, ui);
        new AddCommand(30.00, "Book", "Education").execute(manager, ui);
        new SetBudgetCommand(100.0).execute(manager, ui);
    }

    @Test
    void execute_editAllFields_updatesExpenseSuccessfully() throws Exception {
        EditCommand cmd = new EditCommand(1, 20.00, "Dinner", "Meals");
        cmd.execute(manager, ui);

        Expense edited = manager.getExpense(1);
        assertEquals(20.00, edited.getAmount(), 1e-6);
        assertEquals("Dinner", edited.getDescription());
        assertEquals("Meals", edited.getCategory());
        assertEquals(ui.lastEditedExpense, edited);
    }

    @Test
    void execute_editPartialFields_preservesUnchangedFields() throws Exception {
        EditCommand cmd = new EditCommand(2, null, "Notebook", null);
        cmd.execute(manager, ui);

        Expense edited = manager.getExpense(2);
        assertEquals(30.00, edited.getAmount(), 1e-6); // unchanged
        assertEquals("Notebook", edited.getDescription()); // updated
        assertEquals("Education", edited.getCategory()); // unchanged
        assertEquals(ui.lastEditedExpense, edited);
    }

    @Test
    void execute_editDoesNotAffectOtherExpenses() throws Exception {
        EditCommand cmd = new EditCommand(1, 25.0, "Dinner", null);
        cmd.execute(manager, ui);

        Expense edited = manager.getExpense(1);
        Expense untouched = manager.getExpense(2);

        assertEquals("Dinner", edited.getDescription());
        assertEquals("Book", untouched.getDescription()); // still same
    }

    @Test
    void execute_editPreservesMarkStatus() throws Exception {
        // mark the first expense before editing
        new MarkCommand(1).execute(manager, ui);

        EditCommand cmd = new EditCommand(1, 15.0, "Lunch with friends", null);
        cmd.execute(manager, ui);

        Expense edited = manager.getExpense(1);
        assertTrue(edited.isMarked(), "Edited expense should remain marked");
    }
}

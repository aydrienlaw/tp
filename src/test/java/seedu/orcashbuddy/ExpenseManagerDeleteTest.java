package seedu.orcashbuddy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseManagerDeleteTest {

    static class StubUi extends Ui {
        boolean deleteUsageShown = false;
        String lastErrorMessage = null;
        Expense deletedExpense = null;

        @Override
        public void showDeleteUsage(String errorMessage) {
            deleteUsageShown = true;
            lastErrorMessage = errorMessage;
        }

        @Override
        public void showDeletedExpense(Expense expense) {
            deletedExpense = expense;
        }
    }

    @Test
    void handleDelete_validIndex_deletesExpenseAndShowsConfirmation() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        // Add an expense first
        mgr.handleAdd("add a/20 desc/lunch");

        // Delete it
        mgr.handleDelete("delete 1");

        assertFalse(ui.deleteUsageShown, "Usage message should not be shown");
        assertNotNull(ui.deletedExpense, "Deleted expense should not be null");
        assertEquals(20, ui.deletedExpense.getAmount(), 1e-9);
        assertEquals("lunch", ui.deletedExpense.getDescription());
    }

    @Test
    void handleDelete_emptyInput_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleDelete("delete");

        assertTrue(ui.deleteUsageShown, "Usage message should be shown for empty input");
        assertNotNull(ui.lastErrorMessage, "Error message should be provided");
        assertNull(ui.deletedExpense, "No expense should be deleted");
    }

    @Test
    void handleDelete_nonNumericIndex_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleDelete("delete abc");

        assertTrue(ui.deleteUsageShown, "Usage message should be shown for non-numeric index");
        assertNotNull(ui.lastErrorMessage, "Error message should be provided");
        assertNull(ui.deletedExpense, "No expense should be deleted");
    }

    @Test
    void handleDelete_indexTooLow_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/15 desc/coffee");

        mgr.handleDelete("delete 0"); // index < 1
        assertTrue(ui.deleteUsageShown, "Usage message should be shown for index < 1");
        assertNotNull(ui.lastErrorMessage, "Error message should be provided");
        assertNull(ui.deletedExpense, "No expense should be deleted");
    }

    @Test
    void handleDelete_indexTooHigh_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/15 desc/coffee");

        mgr.handleDelete("delete 2"); // index > size
        assertTrue(ui.deleteUsageShown, "Usage message should be shown for index > size");
        assertNotNull(ui.lastErrorMessage, "Error message should be provided");
        assertNull(ui.deletedExpense, "No expense should be deleted");
    }
}

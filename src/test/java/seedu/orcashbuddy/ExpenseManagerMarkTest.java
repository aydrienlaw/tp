package seedu.orcashbuddy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpenseManagerMarkTest {
    static class StubUi extends Ui {
        boolean markUsageShown = false;
        boolean unmarkUsageShown = false;
        Expense markedExpense = null;
        Expense unmarkedExpense = null;
        String lastMarkErrorMessage = null;
        String lastUnmarkErrorMessage = null;


        @Override
        public void showMarkUsage(String errorMessage) {
            markUsageShown = true;
            lastMarkErrorMessage = errorMessage;
        }

        @Override
        public void showUnmarkUsage(String errorMessage) {
            unmarkUsageShown = true;
            lastUnmarkErrorMessage = errorMessage;
        }

        @Override
        public void showMarkedExpense(Expense expense) {
            markedExpense = expense;
        }

        @Override
        public void showUnmarkedExpense(Expense expense) {
            unmarkedExpense = expense;
        }
    }

    // Test: successfully marking an expense
    @Test
    void handleMarkUnmark_validIndexToMark_marksExpenseAndShowsConfirmation() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/10.00 desc/drinks");

        mgr.handleMarkUnmark("mark 1", true);

        assertFalse(ui.markUsageShown);
        assertNotNull(ui.markedExpense);
        assertTrue(ui.markedExpense.isMarked());
        assertEquals(10.00, ui.markedExpense.getAmount(), 1e-9);
    }

    // Test: successfully unmarking an expense
    @Test
    void handleMarkUnmark_validIndexToUnmark_unmarksExpenseAndShowsConfirmation() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        // Add and mark an expense first
        mgr.handleAdd("add a/15.50 desc/rc4 merchandise");
        mgr.handleMarkUnmark("mark 1", true);

        // Now unmark it
        mgr.handleMarkUnmark("unmark 1", false);

        assertFalse(ui.unmarkUsageShown);
        assertNotNull(ui.unmarkedExpense);
        assertFalse(ui.unmarkedExpense.isMarked());
    }

    // Test: invalid index (too high)
    @Test
    void handleMarkUnmark_indexOutOfBounds_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/5.00 desc/snack");

        mgr.handleMarkUnmark("mark 2", true);

        assertTrue(ui.markUsageShown);
        assertNull(ui.markedExpense);
    }

    // Test: invalid index (zero)
    @Test
    void handleMarkUnmark_zeroIndex_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/5.00 desc/rc4 lanyard");

        mgr.handleMarkUnmark("mark 0", true);

        assertTrue(ui.markUsageShown);
        assertNull(ui.markedExpense);
    }

    // Test: missing index
    @Test
    void handleMarkUnmark_missingIndex_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleMarkUnmark("mark", true);

        assertTrue(ui.markUsageShown);
        assertNull(ui.markedExpense);
    }

    // Test: invalid index format (not a number)
    @Test
    void handleMarkUnmark_invalidIndexFormat_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/5.00 desc/rc4 sticker");

        mgr.handleMarkUnmark("mark abc", true);

        assertTrue(ui.markUsageShown);
        assertNull(ui.markedExpense);
    }

    @Test
    void parseMarkUnmarkCommand_validIndex_returnsIndex() throws MarkUnmarkCommandException {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/10.00 desc/test");

        int index = mgr.parseMarkUnmarkCommand("mark 1", true);
        assertEquals(1, index);
    }

    @Test
    void parseMarkUnmarkCommand_invalidIndex_throwsException() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        assertThrows(MarkUnmarkCommandException.class,
                () -> mgr.parseMarkUnmarkCommand("mark abc", true));
    }

    @Test
    void parseMarkUnmarkCommand_missingIndex_throwsException() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        assertThrows(MarkUnmarkCommandException.class,
                () -> mgr.parseMarkUnmarkCommand("mark", true));
    }
}

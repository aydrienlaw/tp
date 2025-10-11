package seedu.orcashbuddy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseManagerAddTest {
    // Minimal stub to capture UI interactions (no console output)
    static class StubUi extends Ui {
        boolean addUsageShown = false;
        Expense addedExpense = null;

        @Override
        public void showAddUsage() {
            addUsageShown = true;
        }

        @Override
        public void showNewExpense(Expense expense) {
            addedExpense = expense;
        }
    }

    @Test
    void handleAdd_validAmountAndDescription_addsExpenseAndShowsConfirmation() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/12.50 desc/lunch at canteen");

        assertFalse(ui.addUsageShown);
        assertNotNull(ui.addedExpense);
        assertEquals(12.50, ui.addedExpense.getAmount(), 1e-9);
        assertEquals("lunch at canteen", ui.addedExpense.getDescription());
    }

    @Test
    void handleAdd_missingAmountPrefix_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add desc/coffee");

        assertTrue(ui.addUsageShown);
        assertNull(ui.addedExpense);
    }

    @Test
    void handleAdd_nonPositiveAmount_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleAdd("add a/0 desc/zero?");

        assertTrue(ui.addUsageShown);
        assertNull(ui.addedExpense);
    }

    @Test
    void parseAddCommand_validInput_returnsExpense() throws AddCommandException {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        Expense expense = mgr.parseAddCommand("add a/42.00 desc/Test lunch");

        assertEquals(42.00, expense.getAmount(), 1e-9);
        assertEquals("Test lunch", expense.getDescription());
    }

    @Test
    void parseAddCommand_invalidAmount_throwsAddCommandException() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        AddCommandException ex = assertThrows(AddCommandException.class,
                () -> mgr.parseAddCommand("add a/-5 desc/bad amount"));
        assertTrue(ex.getMessage().contains("greater than 0"));
    }

    @Test
    void parseAddCommand_missingDescription_throwsAddCommandException() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        assertThrows(AddCommandException.class,
                () -> mgr.parseAddCommand("add a/2.50 desc/ "));
    }
}

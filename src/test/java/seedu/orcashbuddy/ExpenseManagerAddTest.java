package seedu.orcashbuddy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}

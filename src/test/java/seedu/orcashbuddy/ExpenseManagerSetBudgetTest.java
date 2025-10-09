package seedu.orcashbuddy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpenseManagerSetBudgetTest {
    // Minimal stub to capture UI interactions (no console output)
    static class StubUi extends Ui {
        boolean setBudgetUsageShown = false;
        Double setBudget = null;

        @Override
        public void showSetBudgetUsage() {
            setBudgetUsageShown = true;
        }

        @Override
        public void showNewBudget(double budget) {
            setBudget = budget;
        }
    }

    @Test
    public void handleSetBudget_validAmount_setsBudgetAndShowsConfirmation() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleSetBudget("setbudget a/150.75");

        assertFalse(ui.setBudgetUsageShown);
        assertEquals(150.75, ui.setBudget, 1e-9);
    }

    @Test
    void handleSetBudget_missingAmountPrefix_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleSetBudget("setbudget 150.75");

        assertTrue(ui.setBudgetUsageShown);
        assertNull(ui.setBudget);
    }

    @Test
    void handleSetBudget_nonPositiveAmount_showsUsage() {
        StubUi ui = new StubUi();
        ExpenseManager mgr = new ExpenseManager(ui);

        mgr.handleSetBudget("setbudget a/-150.75");

        assertTrue(ui.setBudgetUsageShown);
        assertNull(ui.setBudget);
    }
}

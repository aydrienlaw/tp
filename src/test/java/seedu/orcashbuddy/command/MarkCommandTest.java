//@@author muadzyamani
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Command-level tests for marking an expense.
 */
class MarkCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Expense lastMarkedExpense;

        @Override
        public void showMarkedExpense(Expense expense) {
            this.lastMarkedExpense = expense;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
        new AddCommand(30.00, "Groceries").execute(manager, ui);
    }

    @Test
    void execute_marksExpense_showsInUi() throws Exception {
        new MarkCommand(1).execute(manager, ui);

        assertNotNull(ui.lastMarkedExpense);
        assertEquals("[X] [" + Expense.DEFAULT_CATEGORY + "] Groceries - $30.00",
                ui.lastMarkedExpense.formatForDisplay());
    }

    @Test
    void execute_invalidZeroIndex_asserts() {
        assertThrows(AssertionError.class, () -> new MarkCommand(0).execute(manager, ui));
    }

    @Test
    void execute_outOfRangeIndex_throwsOrCashBuddyException() {
        assertThrows(OrCashBuddyException.class, () -> new MarkCommand(2).execute(manager, ui));
    }
}

package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnmarkCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Expense lastUnmarkedExpense;

        @Override
        public void showUnmarkedExpense(Expense expense) {
            lastUnmarkedExpense = expense;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
    }

    @Test
    void execute_unmarksExpense() throws Exception {
        new AddCommand(40.0, "Books", "School").execute(manager, ui);
        new MarkCommand(1).execute(manager, ui);

        new UnmarkCommand(1).execute(manager, ui);

        Expense expense = manager.getExpense(1);
        assertFalse(expense.isMarked());
        assertNotNull(ui.lastUnmarkedExpense);
    }

    @Test
    void execute_invalidIndex_asserts() {
        UnmarkCommand command = new UnmarkCommand(0);
        assertThrows(AssertionError.class, () -> command.execute(manager, ui));
    }
}

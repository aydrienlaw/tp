//@@author muadzyamani
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.BudgetStatus;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to mark an expense as paid.
 */
public class MarkCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(MarkCommand.class.getName());
    private final int index;

    /**
     * Constructs a MarkCommand with the specified expense index.
     *
     * @param index the 1-based index of the expense to mark
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) throws OrCashBuddyException {
        assert index >= 1 : "Index must be at least 1";

        Expense expense = expenseManager.markExpense(index);

        LOGGER.log(Level.INFO, "Marked expense at index {0}: {1}",
                new Object[]{index, expense.getDescription()});

        ui.showSeparator();
        ui.showMarkedExpense(expense);
        BudgetStatus status = expenseManager.determineBudgetStatus();
        if (status != BudgetStatus.OK) {
            double remainingBalance = expenseManager.getRemainingBalance();
            ui.showBudgetStatus(status, remainingBalance);
        }
        ui.showSeparator();
    }
}

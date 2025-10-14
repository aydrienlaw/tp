package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to delete an expense from the expense manager.
 */
public class DeleteCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteCommand.class.getName());
    private final int index;

    /**
     * Constructs a DeleteCommand with the specified expense index.
     *
     * @param index the 1-based index of the expense to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) throws OrCashBuddyException {
        assert index >= 1 : "Index must be at least 1";

        Expense removedExpense = expenseManager.deleteExpense(index);

        LOGGER.log(Level.INFO, "Deleted expense at index {0}: {1}",
                new Object[]{index, removedExpense.getDescription()});

        ui.showDeletedExpense(removedExpense);
    }
}
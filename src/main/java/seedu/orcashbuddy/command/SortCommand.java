//@@author saheer17
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;
import seedu.orcashbuddy.expense.Expense;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a command to sort all expenses in descending order of amount.
 */
public class SortCommand extends Command{

    private static final Logger LOGGER = Logger.getLogger(SortCommand.class.getName());

    /**
     * Executes the sort command by delegating to the ExpenseManager to sort expenses
     * and display the sorted list in the UI.
     *
     * @param expenseManager the expense manager containing all expenses
     * @param ui the user interface to display the sorted expenses
     * @throws OrCashBuddyException if the expense list is empty
     */
    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) throws OrCashBuddyException {
        assert expenseManager != null : "ExpenseManager must not be null";
        assert ui != null : "Ui must not be null";
        LOGGER.info("Executing SortCommand");
        ui.showSeparator();

        if (expenseManager.getSize() == 0) {
            LOGGER.info("Cannot sort expenses - list is empty");
            ui.showEmptyExpenseList();  // prints "No expenses added so far."
        } else {
            List<Expense> sortedExpenses = expenseManager.sortExpenses();
            assert sortedExpenses != null : "Sorted expenses should not be null";
            assert sortedExpenses.size() == expenseManager.getSize() : "Sorted list size mismatch";
            ui.showSortedExpenseList(sortedExpenses);
        }

        LOGGER.info("SortCommand execution completed");
        ui.showSeparator();
    }
}

//@@author saheer17
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;
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
        expenseManager.sortExpenses(ui);
        LOGGER.info("SortCommand execution completed");
    }
}

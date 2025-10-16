//@@author saheer17
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;
import java.util.logging.Logger;


public class SortCommand extends Command{

    private static final Logger LOGGER = Logger.getLogger(SortCommand.class.getName());
    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) throws OrCashBuddyException {
        assert expenseManager != null : "ExpenseManager must not be null";
        assert ui != null : "Ui must not be null";
        LOGGER.info("Executing SortCommand");
        expenseManager.sortExpenses(ui);
        LOGGER.info("SortCommand execution completed");
    }
}

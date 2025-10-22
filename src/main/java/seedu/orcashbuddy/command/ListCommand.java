//@@author gumingyoujia
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Logger;

/**
 * Command to list all expenses with budget summary.
 */
public class ListCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ListCommand.class.getName());

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        LOGGER.fine("Executing list command");
        ui.showSeparator();
        expenseManager.displayList(ui);
        ui.showSeparator();
    }
}

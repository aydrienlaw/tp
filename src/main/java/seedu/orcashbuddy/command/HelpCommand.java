//@@author gumingyoujia
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Logger;

/**
 * Command to display help menu.
 */
public class HelpCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(HelpCommand.class.getName());

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        LOGGER.fine("Executing help command");
        ui.showMenu();
        LOGGER.info("Help menu displayed successfully");
    }
}
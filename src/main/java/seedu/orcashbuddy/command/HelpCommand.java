//@@author gumingyoujia
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Logger;

/**
 * Command to display help information / usage menu.
 */
public class HelpCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(HelpCommand.class.getName());

    /**
     * Shows the available commands and their formats.
     *
     * @param expenseManager the central data model that stores all expenses and budget state (unused)
     * @param ui the UI used to show output to the user
     */
    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        LOGGER.fine("Executing help command");
        ui.showSeparator();
        ui.showMenu();
        LOGGER.info("Help menu displayed successfully");
        ui.showSeparator();
    }
}

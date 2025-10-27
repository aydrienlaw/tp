package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Logger;

/**
 * Command to exit the application gracefully.
 * Displays a farewell message and signals the program to stop running.
 */
public class ByeCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ByeCommand.class.getName());

    /**
     * Shows a goodbye message and visual separators.
     *
     * @param expenseManager the central data model that stores all expenses and budget state (unused)
     * @param ui the UI used to show output to the user
     */
    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        LOGGER.info("Executing bye command");
        ui.showSeparator();
        ui.showGoodbye();
        ui.showSeparator();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} to indicate that the application should exit
     */
    @Override
    public boolean isExit() {
        return true;
    }
}

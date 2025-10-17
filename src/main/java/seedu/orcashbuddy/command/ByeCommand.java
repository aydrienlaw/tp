package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Logger;

/**
 * Command to exit the application gracefully.
 */
public class ByeCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ByeCommand.class.getName());

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        LOGGER.info("Executing bye command");
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

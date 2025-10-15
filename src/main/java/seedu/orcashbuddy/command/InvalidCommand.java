//@@author aydrienlaw
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

/**
 * Command representing an invalid or unknown command.
 */
public class InvalidCommand extends Command {

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        ui.showUnknownCommand();
    }
}
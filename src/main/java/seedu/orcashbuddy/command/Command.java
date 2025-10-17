package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

public abstract class Command {
    public abstract void execute(ExpenseManager expenseManager, Ui ui) throws Exception;

    /**
     * Indicates whether executing this command should terminate the application.
     *
     * @return true if the application should exit after execution; false otherwise
     */
    public boolean isExit() {
        return false;
    }
}

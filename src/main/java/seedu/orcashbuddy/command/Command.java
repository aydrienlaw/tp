//@@author aydrienlaw
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

/**
 * Base type for all executable user commands.
 * <p>
 * Each concrete subclass implements {@link #execute(ExpenseManager, Ui)} to
 * perform its logic (e.g. add an expense, list expenses, etc.).
 * After execution, {@link #isExit()} may signal the main loop to terminate.
 */
public abstract class Command {

    /**
     * Executes the command logic.
     *
     * @param expenseManager the central data model that stores all expenses and budget state
     * @param ui the UI used to show output to the user
     * @throws Exception if command execution fails
     */
    public abstract void execute(ExpenseManager expenseManager, Ui ui) throws Exception;

    /**
     * Indicates whether executing this command should terminate the application.
     *
     * @return {@code true} if the application should exit after execution; {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}

// InvalidParsedCommand.java
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

/**
 * Command representing a failed command parse with specific error context.
 */
public class InvalidParsedCommand extends Command {
    private final OrCashBuddyException exception;

    /**
     * Constructs an InvalidParsedCommand with the parse exception details.
     *
     * @param exception the exception containing error details
     */
    public InvalidParsedCommand(OrCashBuddyException exception) {
        this.exception = exception;
    }

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        String errorMessage = exception.getMessage();

        // Determine which usage message to show based on error context
        if (errorMessage.contains("'add'") || errorMessage.contains("desc/") ||
                errorMessage.contains("Description")) {
            ui.showAddUsage();
        } else if (errorMessage.contains("'delete'")) {
            ui.showDeleteUsage(errorMessage);
        } else if (errorMessage.contains("budget") || errorMessage.contains("Budget")) {
            ui.showSetBudgetUsage();
        } else if (errorMessage.contains("'mark'")) {
            ui.showMarkUsage(errorMessage);
        } else if (errorMessage.contains("'unmark'")) {
            ui.showUnmarkUsage(errorMessage);
        } else {
            // Generic error for parsing issues
            System.out.println(errorMessage);
        }
    }
}
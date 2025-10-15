//@@author aydrienlaw
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

/**
 * Command to set the budget amount.
 */
public class SetBudgetCommand extends Command {
    private final double budget;

    /**
     * Constructs a SetBudgetCommand with the specified budget amount.
     *
     * @param budget the budget amount to set
     */
    public SetBudgetCommand(double budget) {
        this.budget = budget;
    }

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        assert budget > 0.0 : "Budget must be positive";

        expenseManager.setBudget(budget);
        ui.showNewBudget(budget);
    }
}

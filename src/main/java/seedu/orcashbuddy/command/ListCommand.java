//@@author gumingyoujia
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.List;
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
        double budget = expenseManager.getBudget();
        double totalExpenses = expenseManager.getTotalExpenses();
        double remainingBalance = expenseManager.getRemainingBalance();
        List<Expense> expenses = expenseManager.getExpenses();
        ui.showFinancialSummary(budget, totalExpenses, remainingBalance, expenses);
        ui.showSeparator();
    }
}

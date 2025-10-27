//@@author limzerui
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to add a new expense to the expense manager.
 */
public class AddCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AddCommand.class.getName());
    private final double amount;
    private final String description;
    private final String category;

    /**
     * Constructs an AddCommand with the specified amount and description, using the default category.
     *
     * @param amount the expense amount
     * @param description the expense description
     */
    public AddCommand(double amount, String description) {
        this(amount, description, Expense.DEFAULT_CATEGORY);
    }

    /**
     * Constructs an {@code AddCommand} with the specified amount and description,
     * using the default category {@link Expense#DEFAULT_CATEGORY}.
     *
     * @param amount the expense amount
     * @param description the expense description
     * @param category the expense category
     */
    public AddCommand(double amount, String description, String category) {
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    /**
     * Adds the expense to the {@link ExpenseManager}, then displays the result via {@link Ui}.
     *
     * @param expenseManager the central data model that stores all expenses and budget state
     * @param ui the UI used to show output to the user
     */
    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        assert amount > 0.0 : "Amount must be positive";
        assert description != null && !description.isBlank() : "Description must not be blank";
        assert category != null && !category.isBlank() : "Category must not be blank";

        Expense expense = new Expense(amount, description, category);
        expenseManager.addExpense(expense);

        LOGGER.log(Level.INFO, "Added expense: amount={0}, desc={1}, category={2}",
                new Object[]{amount, description, category});

        ui.showSeparator();
        ui.showNewExpense(expense);
        ui.showSeparator();
    }
}

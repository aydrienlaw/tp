//@@author gumingyoujia
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to edit expense in the expense manager.
 */
public class EditCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(EditCommand.class.getName());
    private final int index;
    private final Double newAmount; // can be null if unchanged
    private final String newDescription;
    private final String newCategory;

    /**
     * Constructs an EditCommand.
     *
     * @param index index of the expense to edit (1-based)
     * @param newAmount new amount (nullable)
     * @param newDescription new description (nullable)
     * @param newCategory new category (nullable)
     */
    public EditCommand(int index, Double newAmount, String newDescription,
            String newCategory) {
        this.index = index;
        this.newAmount = newAmount;
        this.newDescription = newDescription;
        this.newCategory = newCategory;
    }

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) throws OrCashBuddyException {

        Expense original = expenseManager.getExpense(index);
        if (original == null) {
            throw new OrCashBuddyException("No expense found at index " + index);
        }
        double updatedAmount = (newAmount != null) ? newAmount : original.getAmount();
        String updatedDescription = (newDescription != null) ? newDescription : original.getDescription();
        String updatedCategory = (newCategory != null) ? newCategory : original.getCategory();
        boolean markStatus = original.isMarked();

        Expense edited = new Expense(updatedAmount, updatedDescription, updatedCategory);
        expenseManager.replaceExpense(index, edited);

        if  (markStatus) {
            expenseManager.markExpense(index);
        }

        ui.showEditedExpense(edited);
        expenseManager.checkRemainingBalance(ui);
    }
}

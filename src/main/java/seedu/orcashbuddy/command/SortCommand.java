package seedu.orcashbuddy.command;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;


public class SortCommand extends Command{

    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) throws OrCashBuddyException {
        expenseManager.sortExpenses(ui);
    }
}

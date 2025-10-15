package seedu.orcashbuddy.command;

import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

public abstract class Command {
    public abstract void execute(ExpenseManager expenseManager, Ui ui) throws Exception;
}


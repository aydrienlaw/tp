package seedu.orcashbuddy;

import java.util.ArrayList;

/**
 * Manages a list of expenses. Skeleton placeholder for future implementation.
 */
public class ExpenseManager {
    // To be implemented in later tasks.
    private final Ui ui;
    private final ArrayList<Expense> expenses;

    public ExpenseManager(Ui ui) {
        this.ui = ui;
        this.expenses = new ArrayList<>();
    }

    public void handleAdd(String input) {
        // Expected format: add a/AMOUNT desc/DESCRIPTION
        String rest = input.length() > 3 ? input.substring(3).trim() : "";
        if (!rest.startsWith("a/")) {
            ui.showAddUsage();
            return;
        }

        // Find the start of "desc/" regardless of spacing before it
        int descIdx = rest.indexOf("desc/");
        if (descIdx == -1) {
            ui.showAddUsage();
            return;
        }

        String amountStr = rest.substring(2, descIdx).trim();
        String description = rest.substring(descIdx + 5).trim(); // skip "desc/"

        if (amountStr.isEmpty() || description.isEmpty()) {
            ui.showAddUsage();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (!(amount > 0)) { // catches NaN and <= 0
                ui.showAddUsage();
                return;
            }
            Expense expense = new Expense(amount, description);
            expenses.add(expense);
            ui.showNewExpense(expense);
        } catch (NumberFormatException e) {
            ui.showAddUsage();
        }
    }

    /**
     * Deletes an expense from the list by index.
     * Expected command format: delete EXPENSE_INDEX
     *
     * @param input The full command string entered by the user
     */
    public void handleDelete(String input) {
        // Remove the "delete" part
        String rest = input.length() > 6 ? input.substring(6).trim() : "";
        if (rest.isEmpty()) {
            ui.showDeleteUsage();
            return;
        }

        try {
            int index = Integer.parseInt(rest);

            if (index < 1 || index > expenses.size()) {
                ui.showDeleteUsage();
                return;
            }

            Expense removedExpense = expenses.remove(index - 1);
            ui.showDeletedExpense(removedExpense);

        } catch (NumberFormatException e) {
            ui.showDeleteUsage();
        }
    }


}


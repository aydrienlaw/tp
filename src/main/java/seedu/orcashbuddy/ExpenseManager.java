package seedu.orcashbuddy;

import java.util.ArrayList;

/**
 * Manages a list of expenses. Skeleton placeholder for future implementation.
 */
public class ExpenseManager {
    // To be implemented in later tasks.
    private final Ui ui;
    private final ArrayList<Expense> expenses;
    private double budget = 0.0f;
    private double totalExpenses = 0.0f;
    private double remainingBlance = 0.0f;

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

    /**
     * Marks or unmarks an expense by index.
     * Expected command format: mark/unmark EXPENSE_INDEX
     *
     * @param input The full command string entered by the user
     * @param shouldMark true to mark the expense, false to unmark it
     */
    public void handleMarkUnmark(String input, boolean shouldMark) {
        // Determine command length and extract the rest
        String commandWord = shouldMark ? "mark" : "unmark";
        int commandLength = commandWord.length();
        String rest = input.length() > commandLength ? input.substring(commandLength).trim() : "";

        if (rest.isEmpty()) {
            if (shouldMark) {
                ui.showMarkUsage();
            } else {
                ui.showUnmarkUsage();
            }
            return;
        }

        try {
            int index = Integer.parseInt(rest);

            if (index < 1 || index > expenses.size()) {
                if (shouldMark) {
                    ui.showMarkUsage();
                } else {
                    ui.showUnmarkUsage();
                }
                return;
            }

            Expense expense = expenses.get(index - 1);
            if (shouldMark) {
                expense.mark();
                totalExpenses += expense.getAmount();
                remainingBlance -= expense.getAmount();
                ui.showMarkedExpense(expense);
            } else {
                expense.unmark();
                totalExpenses -= expense.getAmount();
                remainingBlance += expense.getAmount();
                ui.showUnmarkedExpense(expense);
            }

        } catch (NumberFormatException e) {
            if (shouldMark) {
                ui.showMarkUsage();
            } else {
                ui.showUnmarkUsage();
            }
        }
    }

    /**
     * Handles the {@code setbudget} command by parsing user input,
     * validating it, and updating the budget if the input is valid.
     * <p>
     * Expected input format: {@code setbudget a/AMOUNT}
     * where {@code AMOUNT} is a non-negative number.
     * <ul>
     *   <li>If the input is invalid or missing, usage instructions are shown.</li>
     *   <li>If the amount is valid, the budget is updated and a confirmation is displayed.</li>
     * </ul>
     *
     * @param input the full command string entered by the user
     */
    public void handleSetBudget(String input) {
        // Extract arguments after command
        String[] parts = input.trim().split("\\s+", 2);
        String rest = parts.length > 1 ? parts[1] : "";

        if (rest.isEmpty()) {
            ui.showSetBudgetUsage();
            return;
        }

        int amountIdx = rest.indexOf("a/");
        if (amountIdx == -1) {
            ui.showSetBudgetUsage();  // Fixed error message
            return;
        }

        // Extract amount after "a/"
        String amountStr = rest.substring(amountIdx + 2).trim();  // Fixed extraction
        if (amountStr.isEmpty()) {
            ui.showSetBudgetUsage();  // Fixed error message
            return;
        }

        try {
            float newBudget = Float.parseFloat(amountStr);

            if (newBudget < 0) {
                ui.showSetBudgetUsage();
                return;
            }

            this.budget = newBudget;
            ui.showNewBudget(budget);

        } catch (NumberFormatException e) {
            ui.showSetBudgetUsage();
        }
    }

    /**
     * Handles the "list" command by displaying all recorded expenses.
     */
    public void handleList(){
        ui.showListOfExpenses(expenses);
    }

    /**
     * Handles the "statistics" command by displaying a summary of expenses,
     * budget, remaining balance, and the full list of expenses.
     */
    public void handleStatistics(){
        ui.showStatistics(totalExpenses, budget, remainingBlance, expenses);
    }
}


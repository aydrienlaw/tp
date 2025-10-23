package seedu.orcashbuddy.ui;

import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.BudgetStatus;

import java.util.List;

/**
 * Handles user interactions such as displaying messages.
 */
public class Ui {
    private static final String SEPARATOR = "---------------------------------------------------------------\n";
    private static final String ERROR_PREFIX = "[ERROR]: ";
    private static final String CURRENCY_FORMAT = "%.2f";

    // ========== Progress bar constants ==========
    private static final int PROGRESS_BAR_WIDTH = 30;
    private static final String NO_BUDGET_LABEL = "[no budget set]";

    // ANSI color codes
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[0m";

    // ========== Command usage constants ==========
    private static final String ADD_USAGE = "Invalid format. Use: add a/AMOUNT desc/DESCRIPTION [cat/CATEGORY]";
    private static final String DELETE_USAGE = "Invalid format. Use: delete EXPENSE_INDEX";
    private static final String SETBUDGET_USAGE = "Invalid format. Use: setbudget a/AMOUNT";
    private static final String MARK_USAGE = "Invalid format. Use: mark EXPENSE_INDEX";
    private static final String UNMARK_USAGE = "Invalid format. Use: unmark EXPENSE_INDEX";
    private static final String FIND_USAGE = "Invalid format. Use: find cat/CATEGORY or find desc/DESCRIPTION";
    private static final String EDIT_USAGE = "Invalid format. " +
            "Use: edit id/INDEX a/AMOUNT or/and desc/DESCRIPTION or/and cat/CATEGORY";

    // ========== Display separators and decorators ==========
    public void showSeparator() {
        System.out.print(SEPARATOR);
    }

    /**
     * Displays a general error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        showSeparator();
        System.out.println(ERROR_PREFIX + message);
        showSeparator();
    }

    // ========== Welcome and Goodbye messages ==========
    public void showWelcome() {
        System.out.println("Welcome to orCASHbuddy");
        showSeparator();
        showMenu();
        showSeparator();
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    // ========== Menu and help display ==========
    /**
     * Prints the main menu of available commands.
     */
    public void showMenu() {
        String[] menuItems = {
            "Add an expense:                        add a/AMOUNT desc/DESCRIPTION [cat/CATEGORY]",
            "Set a budget:                          setbudget a/AMOUNT",
            "List all expenses & statistics:        list",
            "Find expenses:                         find cat/CATEGORY or find desc/DESCRIPTION",
            "Mark an expense as paid:               mark EXPENSE_INDEX",
            "Mark an expense as unpaid:             unmark EXPENSE_INDEX",
            "Delete an expense:                     delete EXPENSE_INDEX",
            "Edit an expense:                       " +
                    "edit id/INDEX a/AMOUNT or/and desc/DESCRIPTION or/and cat/CATEGORY",
            "Sort all expenses in descending order: sort",
            "Exit the application:                  bye"
        };

        for (String item : menuItems) {
            System.out.println(item);
        }
    }

    // ========== Expense display methods ==========
    /**
     * Helper method to display an expense with a label.
     *
     * @param label The label to display before the expense
     * @param expense The expense to display
     */
    private void showExpenseWithLabel(String label, Expense expense) {
        System.out.println(label);
        System.out.println(expense.formatForDisplay());
    }

    /**
     * Prints the confirmation for a newly added expense.
     */
    public void showNewExpense(Expense expense) {
        showExpenseWithLabel("New Expense:", expense);
    }

    /**
     * Prints the confirmation for an edited expense.
     */
    public void showEditedExpense(Expense expense) {
        showExpenseWithLabel("Edited Expense:", expense);
    }

    /**
     * Displays a confirmation message that an expense was deleted.
     *
     * @param expense The expense object that was deleted
     */
    public void showDeletedExpense(Expense expense) {
        showExpenseWithLabel("Deleted Expense:", expense);
    }

    /**
     * Displays a confirmation message that an expense was marked.
     *
     * @param expense The expense object that was marked
     */
    public void showMarkedExpense(Expense expense) {
        showExpenseWithLabel("Marked Expense:", expense);
    }

    /**
     * Displays a confirmation message that an expense was unmarked.
     *
     * @param expense The expense object that was unmarked
     */
    public void showUnmarkedExpense(Expense expense) {
        showExpenseWithLabel("Unmarked Expense:", expense);
    }

    // ========== Budget-related display methods ==========
    /**
     * Displays a confirmation message showing the newly set total budget.
     *
     * @param budget the new budget amount to display
     */
    public void showNewBudget(double budget) {
        System.out.println("Your total budget is now " + formatCurrency(budget) + ".");
    }

    /**
     * Displays the budget that has been set.
     *
     * @param budget The budget amount.
     */
    public void showBudget(double budget) {
        System.out.println("Budget set: " + formatCurrency(budget));
    }

    /**
     * Displays the total expenses.
     *
     * @param totalExpense The total amount of expenses.
     */
    private void showTotalExpenses(double totalExpense) {
        System.out.println("Total expenses: " + formatCurrency(totalExpense));
    }

    /**
     * Displays the remaining balance after expenses.
     *
     * @param remainingBalance The remaining balance amount.
     */
    private void showRemainingBalance(double remainingBalance) {
        System.out.println("Remaining balance: " + formatCurrency(remainingBalance));
    }

    /**
     * Formats a monetary amount as a currency string.
     *
     * @param amount The amount to format
     * @return Formatted currency string
     */
    private String formatCurrency(double amount) {
        return "$" + String.format(CURRENCY_FORMAT, amount);
    }

    // ========== List display methods ==========
    /**
     * Displays total expenses, budget,
     * remaining balance, and the full list of expenses.
     *
     * @param totalExpense The total amount of expenses.
     * @param budget The budget amount.
     * @param remainingBalance The remaining balance after expenses.
     * @param expenses The list of all expenses.
     */
    public void showFinancialSummary(double budget, double totalExpense,
                                     double remainingBalance, List<Expense> expenses) {
        System.out.println("FINANCIAL SUMMARY");
        showBudget(budget);
        showTotalExpenses(totalExpense);
        showRemainingBalance(remainingBalance);

        System.out.println();
        System.out.println("BUDGET STATUS");
        showProgressBar(budget, totalExpense);

        System.out.println();
        showExpenseList(expenses);
    }

    /**
     * Builds a fixed-width progress bar representing totalExpense/budget.
     * Example: |=========/             |  35%  (Remaining: $390.00)
     * - Uses '/' as the tick marker at the current proportion.
     * - Clamps within [0, PROGRESS_BAR_WIDTH - 1].
     * - Annotates over-budget state.
     */
    private void showProgressBar(double budget, double totalExpense) {
        if (budget <= 0) {
            System.out.println(NO_BUDGET_LABEL);
        }

        double ratio = totalExpense / budget;

        // Determine string colour based on budget usage
        String colour;
        if (ratio > 1.0) {
            colour = ANSI_RED;
        } else if (ratio >= 0.7) {
            colour = ANSI_YELLOW;
        } else {
            colour = ANSI_GREEN;
        }

        // Clamp tick to [0, innerWidth - 1] so bar width stays constant
        int tickPos = (int) Math.round(Math.max(0.0, Math.min(1.0, ratio)) * (PROGRESS_BAR_WIDTH - 1));

        StringBuilder sb = new StringBuilder(PROGRESS_BAR_WIDTH * 2);

        sb.append("Budget Used: ");
        sb.append(colour); // Start color
        sb.append('[');

        // Left fills up to tick
        for (int i = 0; i < tickPos; i += 1) {
            sb.append('█');
        }

        // Tick at current proportion
        sb.append('|');

        // Right spaces after tick until end
        for (int i = tickPos + 1; i < PROGRESS_BAR_WIDTH; i++) {
            sb.append('░');
        }

        sb.append(']');
        sb.append(ANSI_BLACK); // End color

        // Percentage annotation (clamped 0–100)
        double pct = Math.max(0.0, Math.min(100.0, ratio * 100.0));
        sb.append(' ');
        sb.append(String.format("%.2f%%", pct));

        // Over-budget note
        if (ratio > 1.0) {
            sb.append("  (Over by ").append(formatCurrency(totalExpense - budget)).append(')');
        } else {
            sb.append("  (Remaining: ").append(formatCurrency(budget - totalExpense)).append(')');
        }

        System.out.println("Spent: " + formatCurrency(totalExpense) + " / " + formatCurrency(budget));
        System.out.println(sb.toString());
    }

    /**
     * Displays the list of expenses.
     * If the list is empty, a message indicating no expenses is shown.
     *
     * @param expenses The list of expenses to display.
     */
    private void showExpenseList(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            showEmptyExpenseList();
            return;
        }
        System.out.println("Here is your list of expenses:");
        showNumberedExpenses(expenses);
    }

    /**
     * Displays a message for empty list.
     */
    public void showEmptyExpenseList() {
        System.out.println("No expenses added so far.");
    }

    //@@author saheer17
    /**
     * Displays the list of expenses sorted by amount in descending order.
     * If the list is empty, displays a message indicating no expenses.
     *
     * @param sortedExpenses the list of expenses sorted from highest to lowest amount
     */
    public void showSortedExpenseList(List<Expense> sortedExpenses) {
        System.out.println("Here is your list of sorted expenses, starting with the highest amount:");
        showNumberedExpenses(sortedExpenses);
    }

    //@@author muadzyamani
    /**
     * Displays the list of expenses found by the find command.
     * If no expenses are found, displays a message indicating no results.
     *
     * @param foundExpenses the list of expenses that match the search criteria
     * @param searchTerm the term that was searched for
     * @param searchType the type of search performed ("category" or "description")
     */
    public void showFoundExpenses(List<Expense> foundExpenses, String searchTerm, String searchType) {
        if (foundExpenses.isEmpty()) {
            System.out.println("No expenses found matching " + searchType + ": " + searchTerm);
            return;
        }

        System.out.println("Found " + foundExpenses.size() + " expense(s) matching " +
                searchType + ": " + searchTerm);
        showNumberedExpenses(foundExpenses);
    }

    /**
     * Helper method to display a numbered list of expenses.
     *
     * @param expenses The list of expenses to display
     */
    private void showNumberedExpenses(List<Expense> expenses) {
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            assert expense != null : "Expense in list must not be null";
            System.out.println((i + 1) + ". " + expense.formatForDisplay());
        }
    }

    // ========== Command usage methods ==========
    /**
     * Prints the correct usage for the add command.
     */
    public void showAddUsage() {
        System.out.println(ADD_USAGE);
    }

    /**
     * Displays usage information for the delete command.
     * Format: delete EXPENSE_INDEX
     */
    public void showDeleteUsage() {
        System.out.println(DELETE_USAGE);
    }

    /**
     * Displays usage instructions for the {@code setbudget} command
     * when the user provides invalid input.
     * <p>
     * Correct format: {@code setbudget a/AMOUNT}
     */
    public void showSetBudgetUsage() {
        System.out.println(SETBUDGET_USAGE);
    }

    /**
     * Displays usage information for the mark command.
     * Format: mark EXPENSE_INDEX
     */
    public void showMarkUsage() {
        System.out.println(MARK_USAGE);
    }

    /**
     * Displays usage information for the unmark command.
     * Format: unmark EXPENSE_INDEX
     */
    public void showUnmarkUsage() {
        System.out.println(UNMARK_USAGE);
    }

    /**
     * Displays usage information for the find command.
     */
    public void showFindUsage() {
        System.out.println(FIND_USAGE);
    }

    //@@author gumingyoujia
    /**
     * Displays usage information for the edit command.
     */
    public void showEditUsage() {
        System.out.println(EDIT_USAGE);
    }

    /**
     * Displays a message for unknown commands.
     */
    public void showUnknownCommand() {
        System.out.println("Unknown command. Type 'help' to see available commands.");
    }

    // ========== Budget alert methods ==========
    public void showBudgetStatus(BudgetStatus status, double remaining) {
        switch (status) {
        case EXCEEDED -> {
            System.out.println("Alert: You have exceeded your budget!");
            System.out.println("Remaining balance: " + formatCurrency(remaining));
        }
        case EQUAL -> System.out.println("Alert: You have used up your budget!");
        case NEAR -> {
            System.out.println("Alert: Your remaining balance is low.");
            System.out.println("Remaining balance: " + formatCurrency(remaining));
        }
        default -> { /* no-op */ }
        }
    }
}

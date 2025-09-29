package seedu.orcashbuddy;

/**
 * Handles user interactions such as displaying messages.
 */
public class Ui {

    public void showWelcome() {
        System.out.println("Welcome to orCASHbuddy");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints the confirmation for a newly added expense.
     */
    public void showNewExpense(Expense expense) {
        System.out.println("New Expense:");
        System.out.println(expense.formatForDisplay());
    }

    /**
     * Displays a confirmation message that an expense was deleted.
     *
     * @param expense The expense object that was deleted
     */
    public void showDeletedExpense(Expense expense) {
        System.out.println("Deleted Expense:");
        System.out.println(expense.formatForDisplay());
    }

    /**
     * Displays a confirmation message showing the newly set total budget.
     *
     * @param budget the new budget amount to display
     */
    public void showNewBudget(float budget) {
        System.out.println("Your total budget is now $" + String.format("%.2f", budget) + ".");
    }

    /**
     * Displays a confirmation message that an expense was marked.
     *
     * @param expense The expense object that was marked
     */
    public void showMarkedExpense(Expense expense) {
        System.out.println("Marked Expense:");
        System.out.println(expense.formatForDisplay());
    }

    /**
     * Displays a confirmation message that an expense was unmarked.
     *
     * @param expense The expense object that was unmarked
     */
    public void showUnmarkedExpense(Expense expense) {
        System.out.println("Unmarked Expense:");
        System.out.println(expense.formatForDisplay());
    }

    /**
     * Prints the correct usage for the add command.
     */
    public void showAddUsage() {
        System.out.println("Invalid format. Use: add a/AMOUNT desc/DESCRIPTION");
    }

    /**
     * Displays usage information for the delete command.
     * Format: delete EXPENSE_INDEX
     */
    public void showDeleteUsage() {
        System.out.println("Invalid format. Use: delete EXPENSE_INDEX");
    }

    /**
     * Displays usage instructions for the {@code setbudget} command
     * when the user provides invalid input.
     * <p>
     * Correct format: {@code setbudget a/AMOUNT}
     */
    public void showSetBudgetUsage() {
        System.out.println("Invalid format. Use: setbudget a/AMOUNT");
    }


    /**
     * Displays usage information for the mark command.
     * Format: mark EXPENSE_INDEX
     */
    public void showMarkUsage() {
        System.out.println("Invalid format. Use: mark EXPENSE_INDEX");
    }

    /**
     * Displays usage information for the unmark command.
     * Format: unmark EXPENSE_INDEX
     */
    public void showUnmarkUsage() {
        System.out.println("Invalid format. Use: unmark EXPENSE_INDEX");
    }
}

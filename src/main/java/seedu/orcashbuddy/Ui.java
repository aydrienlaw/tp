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
     * Prints the correct usage for the add command.
     */
    public void showAddUsage() {
        System.out.println("Invalid format. Use: add a/AMOUNT desc/DESCRIPTION");
    }
}

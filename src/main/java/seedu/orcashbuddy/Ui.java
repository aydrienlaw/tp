package seedu.orcashbuddy;

import java.util.ArrayList;

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
    public void showNewBudget(double budget) {
        System.out.println("Your total budget is now $" + String.format("%.2f", budget) + ".");
    }

    /**
     * Displays the total expenses.
     *
     * @param totalExpense The total amount of expenses.
     */
    private void showTotalExpenses(double totalExpense){
        System.out.println("Total Expenses: $" + String.format("%.2f", totalExpense));
    }

    /**
     * Displays the budget that has been set.
     *
     * @param budget The budget amount.
     */
    private void showBudget(double budget){
        System.out.println("Budget set: $" + String.format("%.2f", budget));
    }

    /**
     * Displays the remaining balance after expenses.
     *
     * @param remainingBalance The remaining balance amount.
     */
    private void showRemainingBalance(double remainingBalance) {
        System.out.println("Remaining balance: $" + String.format("%.2f", remainingBalance));
    }

    /**
     * Displays the list of expenses.
     * If the list is empty, a message indicating no expenses is shown.
     *
     * @param expenses The list of expenses to display.
     */
    private void showListOfExpenses(ArrayList<Expense> expenses) {
        if  (expenses.isEmpty()) {
            System.out.println("No expenses so far.");
        }
        System.out.println("Here is the list of expenses:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i).formatForDisplay());
        }
    }

    /**
     * Displays total expenses, budget,
     * remaining balance, and the full list of expenses.
     *
     * @param totalExpense The total amount of expenses.
     * @param budget The budget amount.
     * @param remainingBalance The remaining balance after expenses.
     * @param expenses The list of all expenses.
     */
    public void showList(double totalExpense, double budget,
            double remainingBalance, ArrayList<Expense> expenses) {
        showBudget(budget);
        showTotalExpenses(totalExpense);
        showRemainingBalance(remainingBalance);
        showListOfExpenses(expenses);
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
        System.out.println("Type list to view expense indices");
    }

    /**
     * Displays usage information for the unmark command.
     * Format: unmark EXPENSE_INDEX
     */
    public void showUnmarkUsage() {
        System.out.println("Invalid format. Use: unmark EXPENSE_INDEX");
        System.out.println("Type list to view expense indices");
    }


}

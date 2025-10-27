package seedu.orcashbuddy.storage;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.expense.Expense;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages a list of expenses and budget tracking.
 * Provides operations to add, delete, edit, mark, and search expenses,
 * as well as budget management and validation.
 */
public class ExpenseManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());
    private static final double BUDGET_ALERT_THRESHOLD = 10.0;

    // ========== State ==========
    private final List<Expense> expenses;
    private double budget = 0.0;
    private double totalExpenses = 0.0;
    private double remainingBalance = 0.0;

    /**
     * Constructs an ExpenseManager.
     */
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    // ========== Getters ==========
    public double getBudget() {
        return budget;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public int getSize() {
        return expenses.size();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    // ========== Expense Operations ==========

    //@@author limzerui
    /**
     * Adds an expense to the list.
     *
     * @param expense the expense to add
     */
    public void addExpense(Expense expense) {
        validateExpense(expense);

        expenses.add(expense);
        LOGGER.log(Level.INFO, "Added expense amount={0}, desc={1}, category={2}",
                new Object[]{expense.getAmount(), expense.getDescription(), expense.getCategory()});
        LOGGER.fine(() -> "Expense list size is now " + expenses.size());
    }

    //@@author saheer17
    /**
     * Deletes an expense at the specified index.
     *
     * @param index the 1-based index of the expense to delete
     * @return the deleted expense
     * @throws OrCashBuddyException if the index is out of range
     */
    public Expense deleteExpense(int index) throws OrCashBuddyException {
        validateIndex(index);

        Expense removedExpense = expenses.remove(index - 1);
        assert removedExpense != null : "Removed expense should not be null";

        // Rebalance if a marked expense was deleted
        if (removedExpense.isMarked()) {
            updateBudgetAfterUnmark(removedExpense);
            assert totalExpenses >= 0 : "Total expenses became negative after deletion";
        }

        LOGGER.log(Level.INFO, "Deleted expense at index {0}: {1}",
                    new Object[]{index, removedExpense.getDescription()});
        return removedExpense;
    }

    //@@author gumingyoujia

    public Expense getExpense(int index) throws OrCashBuddyException{
        validateIndex(index);

        LOGGER.log(Level.FINE, "Getting expense at index {0}", index);
        return expenses.get(index - 1);
    }

    public void replaceExpense(int index, Expense newExpense) throws OrCashBuddyException {
        validateIndex(index);
        validateExpense(newExpense);

        LOGGER.log(Level.INFO, "Replacing expense at index {0}", index);

        Expense removedExpense = deleteExpense(index);
        expenses.add(index - 1, newExpense);

        if (removedExpense.isMarked()) {
            newExpense.mark();
            updateBudgetAfterUnmark(removedExpense);
        }
    }

    //@@author muadzyamani
    /**
     * Marks an expense at the specified index as paid.
     *
     * @param index the 1-based index of the expense to mark
     * @return the marked expense
     * @throws OrCashBuddyException if the index is out of range
     */
    public Expense markExpense(int index) throws OrCashBuddyException {
        validateIndex(index);

        Expense expense = expenses.get(index - 1);
        if (!expense.isMarked()) {
            expense.mark();
            updateBudgetAfterMark(expense);
        }

        return expense;
    }

    /**
     * Unmarks an expense at the specified index.
     *
     * @param index the 1-based index of the expense to unmark
     * @return the unmarked expense
     * @throws OrCashBuddyException if the index is out of range
     */
    public Expense unmarkExpense(int index) throws OrCashBuddyException {
        validateIndex(index);

        Expense expense = expenses.get(index - 1);
        if (expense.isMarked()) {
            expense.unmark();
            updateBudgetAfterUnmark(expense);
        }

        return expense;
    }

    // ========== Budget Operations ==========

    //@@author aydrienlaw
    /**
     * Sets the budget amount.
     *
     * @param budget the new budget amount
     */
    public void setBudget(double budget) {
        assert budget > 0.0 : "Budget must be positive";

        this.budget = budget;
        recalculateRemainingBalance();

        LOGGER.log(Level.INFO, "Budget set to {0}", budget);
    }

    /**
     * Determines the current budget status based on remaining balance.
     *
     * @return the current budget status
     */
    public BudgetStatus determineBudgetStatus() {
        if (remainingBalance < 0) {
            return BudgetStatus.EXCEEDED;
        } else if (remainingBalance == 0) {
            return BudgetStatus.EQUAL;
        } else if (remainingBalance < BUDGET_ALERT_THRESHOLD) {
            return BudgetStatus.NEAR;
        }
        return BudgetStatus.OK;
    }

    // ========== Display Operations ==========

    //@@author saheer17
    /**
     * Sorts the expenses in descending order by amount and displays them using the UI.
     */
    public List<Expense> sortExpenses() {
        if (expenses.isEmpty()) {
            LOGGER.info("Cannot sort expenses - list is empty");
            return expenses;
        }

        LOGGER.info("Sorting expenses by amount in descending order");
        List<Expense> sortedExpenses = new ArrayList<>(expenses);
        sortedExpenses.sort((e1, e2) -> Double.compare(e2.getAmount(), e1.getAmount()));
        assert sortedExpenses.size() == expenses.size() : "Sorted expenses size should match original expenses size";
        return sortedExpenses;
    }

    // ========== Search Operations ==========

    //@@author muadzyamani
    /**
     * Finds all expenses that match the specified category (case-insensitive).
     *
     * @param category the category to search for
     * @return list of expenses matching the category
     */
    public List<Expense> findExpensesByCategory(String category) {
        validateSearchTerm(category, "Category");

        String searchTerm = category.toLowerCase().trim();
        List<Expense> foundExpenses = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getCategory().toLowerCase().contains(searchTerm)) {
                foundExpenses.add(expense);
            }
        }

        LOGGER.log(Level.INFO, "Found {0} expenses matching category: {1}",
                new Object[]{foundExpenses.size(), category});

        return foundExpenses;
    }

    /**
     * Finds all expenses that match the specified description keyword (case-insensitive).
     *
     * @param keyword the keyword to search for in descriptions
     * @return list of expenses matching the keyword
     */
    public List<Expense> findExpensesByDescription(String keyword) {
        validateSearchTerm(keyword, "Keyword");

        String searchTerm = keyword.toLowerCase().trim();
        List<Expense> foundExpenses = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getDescription().toLowerCase().contains(searchTerm)) {
                foundExpenses.add(expense);
            }
        }

        LOGGER.log(Level.INFO, "Found {0} expenses matching description: {1}",
                new Object[]{foundExpenses.size(), keyword});

        return foundExpenses;
    }

    // ========== Private Helper Methods ==========

    /**
     * Updates budget tracking when an expense is marked as paid.
     * Adds the expense amount to total expenses and recalculates remaining balance.
     *
     * @param expense the expense that was marked
     */
    private void updateBudgetAfterMark(Expense expense) {
        assert expense != null : "Expense must not be null";

        totalExpenses += expense.getAmount();
        recalculateRemainingBalance();

        LOGGER.info(() -> "Updated budget after mark: total=" + totalExpenses +
                ", remaining=" + remainingBalance);
    }

    /**
     * Updates budget tracking when an expense is unmarked.
     * Subtracts the expense amount from total expenses and recalculates remaining balance.
     *
     * @param expense the expense that was unmarked
     */
    private void updateBudgetAfterUnmark(Expense expense) {
        assert expense != null : "Expense must not be null";

        totalExpenses -= expense.getAmount();
        recalculateRemainingBalance();

        LOGGER.info(() -> "Updated budget after unmark: total=" + totalExpenses +
                ", remaining=" + remainingBalance);
    }

    /**
     * Recalculates the remaining balance based on budget and total expenses.
     */
    private void recalculateRemainingBalance() {
        remainingBalance = budget - totalExpenses;
        assert Math.abs(remainingBalance - (budget - totalExpenses)) < 0.001
                : "Remaining balance calculation error";
    }

    // ========== Validation Methods ==========

    //@@author aydrienlaw
    /**
     * Validates that an expense object is valid.
     *
     * @param expense the expense to validate
     * @throws IllegalArgumentException if expense is invalid
     */
    private void validateExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense must not be null");
        }
        if (expense.getAmount() <= 0.0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        if (expense.getDescription().isBlank()) {
            throw new IllegalArgumentException("Expense description must not be blank");
        }
        if (expense.getCategory().isBlank()) {
            throw new IllegalArgumentException("Expense category must not be blank");
        }
    }

    /**
     * Validates that an index is within the valid range.
     *
     * @param index the 1-based index to validate
     * @throws OrCashBuddyException if the index is out of range
     */
    private void validateIndex(int index) throws OrCashBuddyException {
        if (expenses.isEmpty()) {
            throw OrCashBuddyException.emptyExpenseList();
        }
        if (index < 1 || index > expenses.size()) {
            throw OrCashBuddyException.expenseIndexOutOfRange(index, expenses.size());
        }
    }

    /**
     * Validates that a search term is not null or blank.
     *
     * @param searchTerm the search term to validate
     * @param fieldName the name of the field for error messages
     * @throws IllegalArgumentException if search term is invalid
     */
    private void validateSearchTerm(String searchTerm, String fieldName) {
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }
}

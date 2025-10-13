package seedu.orcashbuddy;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages a list of expenses. Skeleton placeholder for future implementation.
 */
public class ExpenseManager {
    // To be implemented in later tasks.
    private static final Logger LOGGER = Logger.getLogger(ExpenseManager.class.getName());
    private final Ui ui;
    private final ArrayList<Expense> expenses;
    private double budget = 0.0;
    private double totalExpenses = 0.0;
    private double remainingBalance = 0.0;

    public ExpenseManager(Ui ui) {
        assert ui != null : "Ui must not be null";
        this.ui = ui;
        this.expenses = new ArrayList<>();
    }

    public void handleAdd(String input) {
        assert input != null : "Add command must not be null";
        LOGGER.fine(() -> "Handling add command: " + input);

        try {
            Expense expense = parseAddCommand(input);
            assert expense.getAmount() > 0.0 : "Parsed expense must be positive";
            assert !expense.getDescription().isBlank() : "Parsed expense description must not be blank";

            expenses.add(expense);
            LOGGER.log(Level.INFO, "Added expense amount={0}, desc={1}",
                    new Object[]{expense.getAmount(), expense.getDescription()});
            LOGGER.fine(() -> "Expense list size is now " + expenses.size());
            ui.showNewExpense(expense);
        } catch (AddCommandException addError) {
            LOGGER.log(Level.WARNING, addError.getMessage(), addError);
            ui.showAddUsage();
        } catch (RuntimeException unexpected) {
            LOGGER.log(Level.SEVERE, "Unexpected error while handling add command", unexpected);
            ui.showAddUsage();
        }
    }

    Expense parseAddCommand(String input) throws AddCommandException {
        // Expected format: add a/AMOUNT desc/DESCRIPTION
        String rest = input.length() > 3 ? input.substring(3).trim() : "";
        if (!rest.startsWith("a/")) {
            throw new AddCommandException("Missing amount prefix 'a/'");
        }

        // Find the start of "desc/" regardless of spacing before it
        int descIdx = rest.indexOf("desc/");
        if (descIdx == -1) {
            throw new AddCommandException("Missing description prefix 'desc/'");
        }

        String amountStr = rest.substring(2, descIdx).trim();
        String description = rest.substring(descIdx + 5).trim(); // skip "desc/"

        if (amountStr.isEmpty()) {
            throw new AddCommandException("Amount is missing after 'a/'");
        }
        if (description.isEmpty()) {
            throw new AddCommandException("Description is missing after 'desc/'");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException nfe) {
            throw new AddCommandException("Amount is not a valid decimal: " + amountStr, nfe);
        }

        if (!(amount > 0)) { // catches NaN and <= 0
            throw new AddCommandException("Amount must be greater than 0: " + amountStr);
        }

        LOGGER.log(Level.FINE, "Parsed add command with amount={0}, description={1}",
                new Object[]{amount, description});

        return new Expense(amount, description);
    }

    /**
     * Handles the deletion of an expense from the list.
     * <p>
     * This method parses the input, validates the index, removes the expense at that index,
     * and updates the UI to show the deleted expense. If the input is invalid or the deletion
     * fails, an error message is displayed via the UI.
     *
     * @param input the full delete command input entered by the user
     */
    public void handleDelete(String input) {
        assert input != null : "Delete command input must not be null";
        LOGGER.fine(() -> "handleDelete called with input: " + input);

        try {
            int index = parseDeleteCommand(input.toLowerCase());
            assert index >= 1 && index <= expenses.size() : "Parsed index out of valid range";

            Expense removedExpense = expenses.remove(index - 1);
            assert removedExpense != null : "Removed expense should not be null";
            LOGGER.log(Level.INFO, "Deleted expense at index {0}: {1}",
                    new Object[]{index, removedExpense.getDescription()});
            ui.showDeletedExpense(removedExpense);

        } catch (DeleteCommandException e) {
            LOGGER.log(Level.WARNING, "Failed to delete expense: " + e.getMessage(), e);
            ui.showDeleteUsage(e.getMessage());
        }
    }

    /**
     * Parses the input string for the delete command and returns the expense index.
     * <p>
     * Validates that the input contains a numeric index and that the index is within
     * the bounds of the current expenses list. Throws {@link DeleteCommandException}
     * for invalid input.
     *
     * @param input the full delete command input entered by the user
     * @return the parsed expense index
     * @throws DeleteCommandException if the input is invalid, non-numeric, or out of bounds
     */
    public int parseDeleteCommand(String input) throws DeleteCommandException {
        assert input != null : "Delete command input must not be null";
        assert input.startsWith("delete") : "Input should start with 'delete'";
        LOGGER.fine(() -> "parseDeleteCommand called with input: " + input);
        String rest = input.length() > 6 ? input.substring(6).trim() : "";

        if (rest.isEmpty()) {
            LOGGER.warning("Delete command missing index");
            throw new DeleteCommandException("Missing expense index after 'delete' command");
        }

        int index;

        try {
            index = Integer.parseInt(rest);
            LOGGER.fine(() -> "Parsed index: " + index);
        } catch (NumberFormatException e) {
            LOGGER.warning("Failed to parse index: " + rest);
            throw new DeleteCommandException("Expense index must be an integer", e);
        }

        if (index < 1) {
            LOGGER.warning("Index less than 1: " + index);
            throw new DeleteCommandException("Expense index must be at least 1");
        }

        if (index > expenses.size()) {
            LOGGER.warning("Index exceeds expenses size: " + index);
            throw new DeleteCommandException("Expense index exceeds number of expenses." +
                    "Max index value possible: " + expenses.size());
        }

        return index;
    }

    /**
     * Marks or unmarks an expense by index.
     * Expected command format: mark/unmark EXPENSE_INDEX
     *
     * @param input The full command string entered by the user
     * @param shouldMark true to mark the expense, false to unmark it
     */
    public void handleMarkUnmark(String input, boolean shouldMark) {
        assert input != null : "Mark/Unmark command input must not be null";
        String commandWord = shouldMark ? "mark" : "unmark";
        LOGGER.fine(() -> "Handling " + commandWord + " command: " + input);

        try {
            int index = parseMarkUnmarkCommand(input.trim().toLowerCase(), shouldMark);
            assert index >= 1 && index <= expenses.size() : "Parsed index out of valid range";

            Expense expense = expenses.get(index - 1);
            assert expense != null : "Expense at index should not be null";

            if (shouldMark) {
                expense.mark();
                updateBudgetAfterMark(expense);
                LOGGER.log(Level.INFO, "Marked expense at index {0}: {1}",
                        new Object[]{index, expense.getDescription()});
                ui.showMarkedExpense(expense);
            } else {
                expense.unmark();
                updateBudgetAfterUnmark(expense);
                LOGGER.log(Level.INFO, "Unmarked expense at index {0}: {1}",
                        new Object[]{index, expense.getDescription()});
                ui.showUnmarkedExpense(expense);
            }

        } catch (MarkUnmarkCommandException e) {
            LOGGER.log(Level.WARNING, "Failed to " + commandWord + " expense: " + e.getMessage());
            if (shouldMark) {
                ui.showMarkUsage(e.getMessage());
            } else {
                ui.showUnmarkUsage(e.getMessage());
            }
        }
    }

    /**
     * Updates budget tracking when an expense is marked as paid.
     * Adds the expense amount to total expenses and recalculates remaining balance.
     *
     * @param expense the expense that was marked
     */
    private void updateBudgetAfterMark(Expense expense) {
        assert expense != null : "Expense must not be null";
        totalExpenses += expense.getAmount();
        remainingBalance = budget - totalExpenses;
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
        remainingBalance = budget - totalExpenses;
        LOGGER.info(() -> "Updated budget after unmark: total=" + totalExpenses +
                ", remaining=" + remainingBalance);
    }

    /**
     * Parses the input string for the mark/unmark command and returns the expense index.
     * <p>
     * Validates that the input contains a numeric index and that the index is within
     * the bounds of the current expenses list. Throws {@link MarkUnmarkCommandException}
     * for invalid input.
     * <p>
     * The parsing is case-insensitive and handles leading/trailing whitespace gracefully.
     *
     * @param input the full mark/unmark command input entered by the user (should be trimmed and lowercased)
     * @param shouldMark true if parsing a mark command, false for unmark
     * @return the parsed expense index
     * @throws MarkUnmarkCommandException if the input is invalid, non-numeric, or out of bounds
     */
    public int parseMarkUnmarkCommand(String input, boolean shouldMark) throws MarkUnmarkCommandException {
        assert input != null : "Mark/Unmark command input must not be null";
        String commandWord = shouldMark ? "mark" : "unmark";
        assert input.startsWith(commandWord) : "Input should start with '" + commandWord + "'";
        LOGGER.fine(() -> "parseMarkUnmarkCommand called with input: " + input);

        int commandLength = commandWord.length();
        String rest = input.length() > commandLength ? input.substring(commandLength).trim() : "";

        if (rest.isEmpty()) {
            LOGGER.warning(commandWord + " command missing index");
            throw new MarkUnmarkCommandException("Missing expense index after '" + commandWord + "' command");
        }

        int index;

        try {
            index = Integer.parseInt(rest);
            LOGGER.fine(() -> "Parsed index: " + index);
        } catch (NumberFormatException e) {
            LOGGER.warning("Failed to parse index: " + rest);
            throw new MarkUnmarkCommandException("Expense index must be an integer", e);
        }

        if (index < 1) {
            LOGGER.warning("Index less than 1: " + index);
            throw new MarkUnmarkCommandException("Expense index must be at least 1");
        }

        if (index > expenses.size()) {
            LOGGER.warning("Index exceeds expenses size: " + index);
            throw new MarkUnmarkCommandException("Expense index exceeds number of expenses. " +
                    "Max index value possible: " + expenses.size());
        }

        return index;
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
            ui.showSetBudgetUsage();
            return;
        }

        // Extract amount after "a/"
        String amountStr = rest.substring(amountIdx + 2).trim();
        if (amountStr.isEmpty()) {
            ui.showSetBudgetUsage();
            return;
        }

        try {
            double newBudget = Double.parseDouble(amountStr);

            if (newBudget <= 0) {
                ui.showSetBudgetUsage();
                return;
            }

            this.budget = newBudget;
            remainingBalance = budget - totalExpenses;
            ui.showNewBudget(budget);

        } catch (NumberFormatException e) {
            ui.showSetBudgetUsage();
        }
    }

    /**
     * Handles the "list" command by displaying a summary of expenses,
     * budget, remaining balance, and the full list of expenses.
     */
    public void handleList(){
        ui.showList(totalExpenses, budget, remainingBalance, expenses);
    }

    /**
     * Handles the "help" command by displaying the menu.
     */
    public void handleHelp(){
        ui.showMenu();
    }
}

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

    public void handleDelete(String input) {
        String rest = input.length() > 6 ? input.substring(6).trim() : "";
        int index = parseDeleteCommand(rest);

        if (index < 1 || index > expenses.size()) {
            ui.showDeleteUsage();
            return;
        }

        Expense removedExpense = expenses.remove(index - 1);
        ui.showDeletedExpense(removedExpense);
    }

    int parseDeleteCommand(String rest) {
        if (rest.isEmpty()) {
            return -1; // invalid
        }

        try {
            return Integer.parseInt(rest);
        } catch (NumberFormatException e) {
            return -1; // invalid
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
                remainingBalance = budget - totalExpenses;
                ui.showMarkedExpense(expense);
            } else {
                expense.unmark();
                totalExpenses -= expense.getAmount();
                remainingBalance = budget - totalExpenses;
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

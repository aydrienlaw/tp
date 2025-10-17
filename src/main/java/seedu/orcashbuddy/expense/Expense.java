package seedu.orcashbuddy.expense;

/**
 * Represents an expense with an amount, description, and category.
 */
public class Expense {
    public static final String DEFAULT_CATEGORY = "Uncategorized";
    private final double amount;
    private final String description;
    private final String category;
    private boolean isMarked;

    public Expense(double amount, String description, String category) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.isMarked = false;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isMarked() {
        return isMarked;
    }

    /**
     * Marks this expense as completed.
     */
    public void mark() {
        this.isMarked = true;
    }

    /**
     * Unmarks this expense.
     */
    public void unmark() {
        this.isMarked = false;
    }

    /**
     * Returns the display format: "[X] DESCRIPTION - $XX.XX" if marked,
     * "[ ] DESCRIPTION - $XX.XX" if unmarked.
     */
    public String formatForDisplay() {
        String statusIcon = isMarked ? "[X]" : "[ ]";
        return statusIcon + " [" + category + "] " + description + " - $"
                + String.format("%.2f", amount);
    }
}

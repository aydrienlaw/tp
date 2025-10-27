package seedu.orcashbuddy.expense;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an immutable expense entry consisting of:
 * <ul>
 *     <li>amount — how much was spent</li>
 *     <li>description — what it was for</li>
 *     <li>category — user-defined grouping</li>
 *     <li>isMarked — whether it's marked/paid</li>
 * </ul>
 */
public class Expense implements Serializable{
    public static final String DEFAULT_CATEGORY = "Uncategorized";
    @Serial
    private static final long serialVersionUID = 1L;
    private final double amount;
    private final String description;
    private final String category;
    private boolean isMarked;

    /**
     * Creates a new {@code Expense}.
     *
     * @param amount the monetary amount of the expense
     * @param description human-readable description
     * @param category the category label (e.g. "Food", "Transport")
     */
    public Expense(double amount, String description, String category) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.isMarked = false;
    }

    /**
     * Returns the amount of this expense.
     *
     * @return the expense amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the description of this expense.
     *
     * @return the expense description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the category of this expense.
     *
     * @return the category label
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns whether this expense is marked/paid.
     *
     * @return {@code true} if marked, {@code false} otherwise
     */
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
     * Returns a human-readable display string for this expense.
     * Format:
     * <pre>
     * [X] [CATEGORY] DESCRIPTION - $XX.XX   (if marked)
     * [ ] [CATEGORY] DESCRIPTION - $XX.XX   (if unmarked)
     * </pre>
     *
     * @return formatted string for list display
     */
    public String formatForDisplay() {
        String statusIcon = isMarked ? "[X]" : "[ ]";
        return statusIcon + " [" + category + "] " + description + " - $"
                + String.format("%.2f", amount);
    }
}

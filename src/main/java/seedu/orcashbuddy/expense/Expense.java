package seedu.orcashbuddy.expense;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an expense with an amount, description, and category.
 */
public class Expense implements Serializable{
    public static final String DEFAULT_CATEGORY = "Uncategorized";
    @Serial
    private static final long serialVersionUID = 1L;
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
     * Returns the display format: "[X] [CATEGORY] DESCRIPTION - $XX.XX" if marked,
     * "[ ] [CATEGORY] DESCRIPTION - $XX.XX" if unmarked.
     */
    public String formatForDisplay() {
        String statusIcon = isMarked ? "[X]" : "[ ]";
        return statusIcon + " [" + category + "] " + description + " - $"
                + String.format("%.2f", amount);
    }
}

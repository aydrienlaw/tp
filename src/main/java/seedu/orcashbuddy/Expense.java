package seedu.orcashbuddy;

/**
 * Represents an expense with an amount and description.
 */
public class Expense {
    private final double amount;
    private final String description;
    private boolean isMarked;

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
        this.isMarked = false;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
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
        return statusIcon + " " + description + " - $" + String.format("%.2f", amount);
    }
}

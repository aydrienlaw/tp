package seedu.orcashbuddy;

/**
 * Represents an expense with an amount and description.
 */
public class Expense {
    private final double amount;
    private final String description;

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns the display format: "[ ] DESCRIPTION - $XX.XX".
     */
    public String formatForDisplay() {
        return "[ ] " + description + " - $" + String.format("%.2f", amount);
    }
}

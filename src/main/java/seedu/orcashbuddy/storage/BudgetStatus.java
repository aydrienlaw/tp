package seedu.orcashbuddy.storage;

/**
 * Represents the financial status of the user's budget.
 */
public enum BudgetStatus {
    EXCEEDED,   // Total expenses exceed budget
    EQUAL,      // Total expenses equal budget
    NEAR,       // Remaining balance below threshold
    OK          // Within safe range
}

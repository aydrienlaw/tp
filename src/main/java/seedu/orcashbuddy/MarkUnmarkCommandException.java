package seedu.orcashbuddy;

/**
 * Represents an exception thrown when a mark or unmark command cannot be parsed or executed properly.
 * <p>
 * This exception is used in {@link seedu.orcashbuddy.ExpenseManager} when:
 * <ul>
 *   <li>The mark/unmark command input is missing an index.</li>
 *   <li>The provided index is not a valid integer.</li>
 *   <li>The index is less than 1 or exceeds the number of expenses in the list.</li>
 * </ul>
 */
public class MarkUnmarkCommandException extends Exception {
    /**
     * Constructs a new MarkUnmarkCommandException with the specified detail message.
     *
     * @param message the detail message describing the error
     */
    public MarkUnmarkCommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new MarkUnmarkCommandException with the specified detail message and cause.
     *
     * @param message the detail message describing the error
     * @param cause   the cause of this exception (which can be retrieved later using {@link #getCause()})
     */
    public MarkUnmarkCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

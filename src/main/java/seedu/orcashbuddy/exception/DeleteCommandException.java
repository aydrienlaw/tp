package seedu.orcashbuddy.exception;

import seedu.orcashbuddy.storage.ExpenseManager;

/**
 * Represents an exception thrown when a delete command cannot be parsed or executed properly.
 * <p>
 * This exception is used in {@link ExpenseManager} when:
 * <ul>
 *   <li>The delete command input is missing an index.</li>
 *   <li>The provided index is not a valid integer.</li>
 *   <li>The index is less than 1 or exceeds the number of expenses in the list.</li>
 * </ul>
 */
public class DeleteCommandException extends Exception {
    /**
     * Constructs a new DeleteCommandException with the specified detail message.
     *
     * @param message the detail message describing the error
     */
    public DeleteCommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new DeleteCommandException with the specified detail message and cause.
     *
     * @param message the detail message describing the error
     * @param cause   the cause of this exception (which can be retrieved later using {@link #getCause()})
     */
    public DeleteCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

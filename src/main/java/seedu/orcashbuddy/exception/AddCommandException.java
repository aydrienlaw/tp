package seedu.orcashbuddy.exception;

/**
 * Signals that the {@code add} command input is invalid and the expense
 * could not be parsed.
 */
public class AddCommandException extends Exception {

    /**
     * Creates an exception that describes why parsing failed.
     *
     * @param message human-readable explanation of the failure
     */
    public AddCommandException(String message) {
        super(message);
    }

    /**
     * Creates an exception that wraps a lower-level cause encountered while parsing.
     *
     * @param message contextual information about the failure
     * @param cause   the underlying exception
     */
    public AddCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

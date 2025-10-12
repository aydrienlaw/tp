package seedu.orcashbuddy;

/**
 * Exception thrown when a delete command cannot be parsed or executed properly.
 */
public class DeleteCommandException extends Exception {
    public DeleteCommandException(String message) {
        super(message);
    }

    public DeleteCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

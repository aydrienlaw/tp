package seedu.orcashbuddy;

/**
 * Exception thrown when an error occurs while executing a list command.
 * <p>
 * This exception is used to indicate issues specific to the listing
 * functionality in the OrcashBuddy application, such as invalid parameters
 * or unexpected runtime errors during command execution.
 */
public class ListCommandException extends Exception {

    /**
     * Constructs a new {@code ListCommandException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public ListCommandException(String message) {
        super(message);
    }
}


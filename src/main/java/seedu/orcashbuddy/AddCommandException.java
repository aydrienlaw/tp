package seedu.orcashbuddy;

class AddCommandException extends Exception {
    AddCommandException(String message) {
        super(message);
    }

    AddCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

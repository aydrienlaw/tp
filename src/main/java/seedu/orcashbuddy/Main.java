package seedu.orcashbuddy;

import seedu.orcashbuddy.command.Command;
import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.parser.Parser;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point for the orCASHbuddy application.
 * Prints a welcome message and exits gracefully when the user types "bye".
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final Ui ui;
    private final ExpenseManager expenseManager;
    private final Parser parser;

    static {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.INFO);
        }
    }

    public Main() {
        this.ui = new Ui();
        this.expenseManager = new ExpenseManager();
        this.parser = new Parser();
    }

    public void run() {
        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = readInput(scanner);
            if (input == null) {
                break;
            }

            if (isExitCommand(input)) {
                ui.showGoodbye();
                break;
            }

            executeCommand(input);
        }
    }

    /**
     * Reads user input from the scanner.
     *
     * @param scanner the scanner to read from
     * @return the user input, or null if input is closed
     */
    private String readInput(Scanner scanner) {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            LOGGER.info("Input closed, exiting application");
            return null;
        }
    }

    /**
     * Checks if the input is an exit command.
     *
     * @param input the user input
     * @return true if the input is "bye", false otherwise
     */
    private boolean isExitCommand(String input) {
        String trimmed = input == null ? "" : input.trim();
        return trimmed.equalsIgnoreCase("bye");
    }

    /**
     * Parses and executes a command from user input.
     *
     * @param input the user input string
     */
    private void executeCommand(String input) {
        try {
            Command command = parser.parse(input);
            command.execute(expenseManager, ui);
        } catch (OrCashBuddyException e) {
            // Handle expected application exceptions
            LOGGER.log(Level.INFO, "Application error: " + e.getMessage());
            ui.showError(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected exceptions
            LOGGER.log(Level.WARNING, "Unexpected error executing command: " + e.getMessage(), e);
            ui.showError("An unexpected error occurred while processing your command.");
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
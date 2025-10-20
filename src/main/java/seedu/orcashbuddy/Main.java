package seedu.orcashbuddy;

import seedu.orcashbuddy.command.Command;
import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.parser.Parser;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.storage.StorageManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point for the orCASHbuddy application.
 * Prints a welcome message and delegates exit handling to the command layer.
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
        this.expenseManager = StorageManager.loadExpenseManager(ui);
        this.parser = new Parser();
    }

    public void run() {
        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            String input = readInput(scanner);
            if (input == null) {
                break;
            }

            shouldExit = executeCommand(input);
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
     * Parses and executes a command from user input.
     *
     * @param input the user input string
     * @return true if the executed command signals application exit; false otherwise
     */
    private boolean executeCommand(String input) {
        try {
            Command command = parser.parse(input);
            command.execute(expenseManager, ui);
            StorageManager.saveExpenseManager(expenseManager, ui);
            return command.isExit();
        } catch (OrCashBuddyException e) {
            // Handle expected application exceptions
            LOGGER.log(Level.INFO, "Application error: " + e.getMessage());
            ui.showError(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected exceptions
            LOGGER.log(Level.WARNING, "Unexpected error executing command: " + e.getMessage(), e);
            ui.showError("An unexpected error occurred while processing your command.");
        }
        return false;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

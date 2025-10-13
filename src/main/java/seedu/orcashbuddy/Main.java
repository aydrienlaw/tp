package seedu.orcashbuddy;

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
    private final Ui ui;
    private final ExpenseManager expenseManager;

    static {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.INFO);
        }
    }

    public Main() {
        this.ui = new Ui();
        this.expenseManager = new ExpenseManager(ui);
    }

    public void run() {
        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input;
            try {
                input = scanner.nextLine();
            } catch (NoSuchElementException e) {
                // Input closed (e.g., IDE stopped input). Exit silently.
                break;
            }

            String trimmed = input == null ? "" : input.trim();
            if (trimmed.equalsIgnoreCase("bye")) {
                ui.showGoodbye();
                break;
            }

            String lowerInput = trimmed.toLowerCase();

            if (lowerInput.startsWith("add")) {
                expenseManager.handleAdd(trimmed);
            } else if (lowerInput.startsWith("delete")) {
                expenseManager.handleDelete(trimmed);
            } else if (lowerInput.startsWith("setbudget")) {
                expenseManager.handleSetBudget(trimmed);
            } else if (lowerInput.startsWith("list")) {
                expenseManager.handleList();
            } else if (lowerInput.startsWith("help")) {
                expenseManager.handleHelp();
            } else if (lowerInput.startsWith("unmark")) {
                expenseManager.handleMarkUnmark(trimmed, false);
            } else if (lowerInput.startsWith("mark")) {
                expenseManager.handleMarkUnmark(trimmed, true);
            } else {
                ui.showUnknownCommand();
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

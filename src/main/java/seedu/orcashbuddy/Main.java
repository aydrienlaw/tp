package seedu.orcashbuddy;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Entry point for the orCASHbuddy application.
 * Prints a welcome message and exits gracefully when the user types "bye".
 */
public class Main {
    private final Ui ui;
    private final ExpenseManager expenseManager;

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

            if (trimmed.toLowerCase().startsWith("add")) {
                expenseManager.handleAdd(trimmed);
                continue;
            }
            if (trimmed.toLowerCase().startsWith("delete")) {
                expenseManager.handleDelete(trimmed);
            }
            if (trimmed.toLowerCase().startsWith("mark")) {
                expenseManager.handleMarkUnmark(trimmed, true);
                continue;
            }
            if (trimmed.toLowerCase().startsWith("unmark")) {
                expenseManager.handleMarkUnmark(trimmed, false);
                continue;
            }
            if (trimmed.toLowerCase().startsWith("setbudget")) {
                expenseManager.handleSetBudget(trimmed);
                continue;
            }
            if (trimmed.toLowerCase().startsWith("list")) {
                expenseManager.handleList();
            }
            if (trimmed.toLowerCase().startsWith("help")) {
                expenseManager.handleHelp();
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

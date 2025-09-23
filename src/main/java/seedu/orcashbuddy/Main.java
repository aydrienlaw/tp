package seedu.orcashbuddy;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Entry point for the orCASHbuddy application.
 * Prints a welcome message and exits gracefully when the user types "bye".
 */
public class Main {
    private final Ui ui;

    public Main() {
        this.ui = new Ui();
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
                handleAdd(trimmed);
                continue;
            }

            // Minimal feedback for unknown commands (keep simple)
            ui.showAddUsage();
        }
    }

    private void handleAdd(String input) {
        // Expected format: add a/AMOUNT desc/DESCRIPTION
        String rest = input.length() > 3 ? input.substring(3).trim() : "";
        if (!rest.startsWith("a/")) {
            ui.showAddUsage();
            return;
        }

        // Find the start of "desc/" regardless of spacing before it
        int descIdx = rest.indexOf("desc/");
        if (descIdx == -1) {
            ui.showAddUsage();
            return;
        }

        String amountStr = rest.substring(2, descIdx).trim();
        String description = rest.substring(descIdx + 5).trim(); // skip "desc/"

        if (amountStr.isEmpty() || description.isEmpty()) {
            ui.showAddUsage();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (!(amount > 0)) { // catches NaN and <= 0
                ui.showAddUsage();
                return;
            }
            Expense expense = new Expense(amount, description);
            ui.showNewExpense(expense);
        } catch (NumberFormatException e) {
            ui.showAddUsage();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

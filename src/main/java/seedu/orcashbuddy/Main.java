package seedu.orcashbuddy;

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
            if (!scanner.hasNextLine()) {
                // End-of-input
                ui.showGoodbye();
                break;
            }
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                ui.showGoodbye();
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}


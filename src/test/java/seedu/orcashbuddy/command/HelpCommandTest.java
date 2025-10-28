//@@author gumingyoujia
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Command-level tests for displaying help information.
 */
class HelpCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        List<String> called = new ArrayList<>();

        @Override
        public void showSeparator() {
            called.add("separator");
        }

        @Override
        public void showMenu() {
            called.add("menu");
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
    }

    @Test
    void execute_displaysMenuAndSeparatorsInOrder() {
        new HelpCommand().execute(manager, ui);

        // Ensure the order of calls is correct: separator → menu → separator
        assertEquals(3, ui.called.size(), "Expected three UI calls (sep, menu, sep)");
        assertEquals("separator", ui.called.get(0));
        assertEquals("menu", ui.called.get(1));
        assertEquals("separator", ui.called.get(2));
    }

    @Test
    void execute_logsAndCallsMenuMethod() {
        new HelpCommand().execute(manager, ui);

        // Make sure menu is called at least once
        assertTrue(ui.called.contains("menu"), "Menu should have been displayed");
    }
}

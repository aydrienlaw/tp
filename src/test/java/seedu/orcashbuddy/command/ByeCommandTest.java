package seedu.orcashbuddy.command;

import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ByeCommandTest {

    private static class StubUi extends Ui {
        private boolean goodbyeShown = false;

        @Override
        public void showGoodbye() {
            goodbyeShown = true;
        }

        boolean wasGoodbyeShown() {
            return goodbyeShown;
        }
    }

    @Test
    void execute_showsGoodbyeAndSignalsExit() throws Exception {
        ByeCommand command = new ByeCommand();
        StubUi ui = new StubUi();

        command.execute(new ExpenseManager(), ui);

        assertTrue(ui.wasGoodbyeShown(), "Ui.showGoodbye should be invoked");
        assertTrue(command.isExit(), "ByeCommand should signal application exit");
    }
}

package seedu.orcashbuddy.command;

import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidCommandTest {

    private static class TrackingUi extends Ui {
        boolean addUsageShown;
        boolean unmarkUsageShown;
        boolean unknownShown;

        @Override
        public void showAddUsage() {
            addUsageShown = true;
        }

        @Override
        public void showUnmarkUsage() {
            unmarkUsageShown = true;
        }

        @Override
        public void showUnknownCommand() {
            unknownShown = true;
        }
    }

    @Test
    void execute_withAddException_showsAddUsage() throws Exception {
        TrackingUi ui = new TrackingUi();
        InvalidCommand cmd = new InvalidCommand(new OrCashBuddyException("'add' missing input"));

        cmd.execute(new ExpenseManager(), ui);

        assertTrue(ui.addUsageShown);
    }

    @Test
    void execute_withUnmarkException_showsUnmarkUsage() throws Exception {
        TrackingUi ui = new TrackingUi();
        InvalidCommand cmd = new InvalidCommand(new OrCashBuddyException("'unmark' index"));

        cmd.execute(new ExpenseManager(), ui);

        assertTrue(ui.unmarkUsageShown);
    }

    @Test
    void execute_withoutException_showsUnknown() throws Exception {
        TrackingUi ui = new TrackingUi();
        InvalidCommand cmd = new InvalidCommand();

        cmd.execute(new ExpenseManager(), ui);

        assertTrue(ui.unknownShown);
    }
}

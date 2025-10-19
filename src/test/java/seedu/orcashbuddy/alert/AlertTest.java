//@@author gumingyoujia
package seedu.orcashbuddy.alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link ExpenseManager#checkRemainingBalance(Ui)}.
 */
class AlertTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        boolean nearAlertShown = false;
        boolean equalAlertShown = false;
        boolean exceedAlertShown = false;

        @Override
        public void showNearAlert(double remainingBalance) {
            nearAlertShown = true;
        }

        @Override
        public void showEqualAlert() {
            equalAlertShown = true;
        }

        @Override
        public void showExceedAlert(double remainingBalance) {
            exceedAlertShown = true;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();

        // Set budget to control remaining balance
        new seedu.orcashbuddy.command.SetBudgetCommand(100.0).execute(manager, ui);
    }

    @Test
    void checkRemainingBalance_whenBalanceBelowThreshold_triggersNearAlert() throws Exception {
        new seedu.orcashbuddy.command.AddCommand(95.0, "Dinner").execute(manager, ui);
        new seedu.orcashbuddy.command.MarkCommand(1).execute(manager, ui);

        manager.checkRemainingBalance(ui);

        assertTrue(ui.nearAlertShown);
        assertFalse(ui.equalAlertShown);
        assertFalse(ui.exceedAlertShown);
    }

    @Test
    void checkRemainingBalance_whenBalanceZero_triggersEqualAlert() throws Exception {
        new seedu.orcashbuddy.command.AddCommand(100.0, "Laptop").execute(manager, ui);
        new seedu.orcashbuddy.command.MarkCommand(1).execute(manager, ui);

        manager.checkRemainingBalance(ui);

        assertTrue(ui.equalAlertShown);
        assertFalse(ui.nearAlertShown);
        assertFalse(ui.exceedAlertShown);
    }

    @Test
    void checkRemainingBalance_whenBalanceNegative_triggersExceedAlert() throws Exception {
        new seedu.orcashbuddy.command.AddCommand(120.0, "Shoes").execute(manager, ui);
        new seedu.orcashbuddy.command.MarkCommand(1).execute(manager, ui);

        manager.checkRemainingBalance(ui);

        assertTrue(ui.exceedAlertShown);
        assertFalse(ui.equalAlertShown);
        assertFalse(ui.nearAlertShown);
    }

    @Test
    void checkRemainingBalance_whenBalanceAboveThreshold_noAlert() throws Exception {
        new seedu.orcashbuddy.command.AddCommand(20.0, "Snacks").execute(manager, ui);
        new seedu.orcashbuddy.command.MarkCommand(1).execute(manager, ui);

        manager.checkRemainingBalance(ui);

        assertFalse(ui.nearAlertShown);
        assertFalse(ui.equalAlertShown);
        assertFalse(ui.exceedAlertShown);
    }
}

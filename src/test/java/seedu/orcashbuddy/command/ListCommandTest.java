//@@author gumingyoujia
package seedu.orcashbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Command-level tests for listing expenses.
 */
class ListCommandTest {

    private ExpenseManager manager;
    private StubUi ui;

    static class StubUi extends Ui {
        Double seenTotal = null;
        Double seenBudget = null;
        Double seenRemaining = null;
        List<Expense> lastListedExpenses = null;

        @Override
        public void showFinancialSummary(double budget, double totalExpense,
                                         double remainingBalance, List<Expense> expenses) {
            this.seenTotal = totalExpense;
            this.seenBudget = budget;
            this.seenRemaining = remainingBalance;
            this.lastListedExpenses = expenses;
        }
    }

    @BeforeEach
    void setUp() {
        manager = new ExpenseManager();
        ui = new StubUi();
    }

    @Test
    void execute_withExpensesAndBudget_displaysTotalsAndItems() throws Exception {
        new AddCommand(12.50, "Lunch").execute(manager, ui);
        new AddCommand(7.25, "Coffee").execute(manager, ui);
        new SetBudgetCommand(100.0).execute(manager, ui);

        new ListCommand().execute(manager, ui);

        assertEquals(0.00, ui.seenTotal, 1e-6);
        assertEquals(100.0, ui.seenBudget, 1e-6);
        assertEquals(100.00, ui.seenRemaining, 1e-6);
        assertEquals(2, ui.lastListedExpenses.size());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Lunch - $12.50",
                ui.lastListedExpenses.get(0).formatForDisplay());
        assertEquals("[ ] [" + Expense.DEFAULT_CATEGORY + "] Coffee - $7.25",
                ui.lastListedExpenses.get(1).formatForDisplay());
    }

    @Test
    void execute_noBudget_defaultsToZero() throws Exception {
        new AddCommand(2.00, "Pen").execute(manager, ui);

        new ListCommand().execute(manager, ui);

        assertEquals(0.00, ui.seenTotal, 1e-6);
        assertEquals(0.0, ui.seenBudget, 1e-6);
        assertEquals(0.00, ui.seenRemaining, 1e-6);
        assertEquals(1, ui.lastListedExpenses.size());
    }
}

package seedu.orcashbuddy.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.BudgetStatus;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {

    private Ui ui;
    private PrintStream originalOut;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        originalOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String capturedOutput() {
        return out.toString();
    }

    @Test
    void showMenu_listsCoreCommands() {
        ui.showMenu();
        String output = capturedOutput();
        assertTrue(output.contains("add a/AMOUNT"));
        assertTrue(output.contains("setbudget a/AMOUNT"));
        assertTrue(output.contains("bye"));
    }

    @Test
    void showFinancialSummary_withExpenses_displaysEntries() {
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(12.50, "Lunch", "Food"));
        expenses.add(new Expense(5.20, "Coffee", "Drinks"));

        ui.showFinancialSummary(100.0, 17.70, 82.30, expenses);

        String output = capturedOutput();
        assertTrue(output.contains("FINANCIAL SUMMARY"));
        assertTrue(output.contains("Lunch"));
        assertTrue(output.contains("Coffee"));
    }

    @Test
    void showFinancialSummary_empty_displaysGuidance() {
        ArrayList<Expense> emptyList = new ArrayList<>();
        ui.showFinancialSummary(0.0, 0.0, 0.0, emptyList);
        assertTrue(capturedOutput().contains("No expenses added so far."));
    }

    @Test
    void showBudgetStatus_handlesDifferentStatuses() {
        ui.showBudgetStatus(BudgetStatus.EXCEEDED, -12.3);
        String output = capturedOutput();
        assertTrue(output.contains("exceeded your budget"));

        out.reset();
        ui.showBudgetStatus(BudgetStatus.NEAR, 15.0);
        output = capturedOutput();
        assertTrue(output.contains("remaining balance is low"));

        out.reset();
        ui.showBudgetStatus(BudgetStatus.EQUAL, 0.0);
        output = capturedOutput();
        assertTrue(output.contains("used up your budget"));

        out.reset();
        ui.showBudgetStatus(BudgetStatus.OK, 80.0);
        output = capturedOutput();
        assertTrue(output.isBlank(), "OK status should not print alerts");
    }
}

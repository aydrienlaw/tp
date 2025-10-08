package seedu.orcashbuddy;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ExpenseManagerListTest{

    @Test
    void showList_printsExpectedSectionsAndFormatsCorrectly() {
        Ui ui = new Ui();
        ExpenseManager mgr = new ExpenseManager(ui);
        mgr.handleAdd("add a/12.50 desc/Lunch at canteen");
        mgr.handleAdd("add a/7.8 desc/Grab ride");
        mgr.handleSetBudget("setbudget a/100");
        mgr.handleMarkUnmark("mark 1",true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        mgr.handleList();
        System.setOut(System.out);
        String output = out.toString().trim();

        assertTrue(output.contains("Budget set: $100.00"), "Budget section missing or incorrectly formatted");
        assertTrue(output.contains("Total expenses: $12.50"), "Total expense missing or incorrectly formatted");
        assertTrue(output.contains("Remaining balance: $87.50"), "Remaining balance missing or incorrectly formatted");
        assertTrue(output.contains("Here is the list of expenses:"), "Header missing or incorrectly formatted");
        assertTrue(output.contains("1. [X] Lunch at canteen - $12.50"), "First expense missing or incorrectly formatted");
        assertTrue(output.contains("2. [ ] Grab ride - $7.80"), "Second expense missing or incorrectly formatted");
        assertFalse(output.contains("No expenses so far."), "Should not show 'No expenses so far.'");
    }

    @Test
    void showList_noExpenses_printsNoExpensesMessage() {
        Ui ui = new Ui();
        ExpenseManager mgr = new ExpenseManager(ui);
        mgr.handleSetBudget("setbudget a/50");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        mgr.handleList();
        System.setOut(System.out);

        String output = out.toString().trim();

        assertTrue(output.contains("Budget set: $50.00"));
        assertTrue(output.contains("Total expenses: $0.00"));
        assertTrue(output.contains("Remaining balance: $50.00"));
        assertTrue(output.contains("No expenses so far."));
        assertFalse(output.contains("Here is the list of expenses:"));
    }
}

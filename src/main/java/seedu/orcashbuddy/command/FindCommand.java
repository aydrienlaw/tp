//@@author muadzyamani
package seedu.orcashbuddy.command;

import seedu.orcashbuddy.expense.Expense;
import seedu.orcashbuddy.storage.ExpenseManager;
import seedu.orcashbuddy.ui.Ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to find expenses by category or description.
 */
public class FindCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(FindCommand.class.getName());

    private final String searchType; // "category" or "description"
    private final String searchTerm;

    /**
     * Constructs a {@code FindCommand} with the specified search type and term.
     *
     * @param searchType the type of search ("category" or "description")
     * @param searchTerm the term to search for
     */
    public FindCommand(String searchType, String searchTerm) {
        this.searchType = searchType;
        this.searchTerm = searchTerm;
    }

    /**
     * Searches the {@link ExpenseManager} for matching expenses,
     * and displays the results via {@link Ui}.
     *
     * @param expenseManager the central data model that stores all expenses and budget state
     * @param ui the UI used to show output to the user
     */
    @Override
    public void execute(ExpenseManager expenseManager, Ui ui) {
        assert searchType != null && !searchType.isBlank() : "Search type must not be blank";
        assert searchTerm != null && !searchTerm.isBlank() : "Search term must not be blank";

        LOGGER.log(Level.INFO, "Executing find command: type={0}, term={1}",
                new Object[]{searchType, searchTerm});

        List<Expense> foundExpenses;

        if (searchType.equals("category")) {
            foundExpenses = expenseManager.findExpensesByCategory(searchTerm);
        } else {
            foundExpenses = expenseManager.findExpensesByDescription(searchTerm);
        }

        LOGGER.log(Level.INFO, "Found {0} matching expenses", foundExpenses.size());

        ui.showSeparator();
        ui.showFoundExpenses(foundExpenses, searchTerm, searchType);
        ui.showSeparator();
    }
}

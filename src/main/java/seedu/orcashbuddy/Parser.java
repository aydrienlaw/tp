package seedu.orcashbuddy;

/**
 * Parses user input and routes to the appropriate command handler.
 */
public class Parser {
    private final String commandWord;
    private final String arguments;

    /**
     * Constructs a Parser by parsing the given user input.
     *
     * @param userInput the raw input string from the user
     */
    public Parser(String userInput) {
        String trimmed = userInput.trim();
        String[] words = trimmed.split("\\s+", 2);
        this.commandWord = words[0].toLowerCase();
        this.arguments = (words.length > 1) ? words[1]: "";
    }

    /**
     * Returns the command word (e.g., "add", "delete", "list").
     *
     * @return the command word in lowercase
     */
    public String getCommandWord() {
        return commandWord;
    }

    /**
     * Returns the arguments after the command word.
     *
     * @return the arguments string, or empty string if no arguments
     */
    public String getArguments() {
        return arguments;
    }

    /**
     * Executes the appropriate command based on the parsed input.
     *
     * @param expenseManager the ExpenseManager to perform operations on
     * @param ui the UI to display messages
     * @param userInput the complete user input string
     */
    public void executeCommand(ExpenseManager expenseManager, Ui ui, String userInput) {
        switch (commandWord) {
        case "add":
            expenseManager.handleAdd(userInput);
            break;
        case "delete":
            expenseManager.handleDelete(userInput);
            break;
        case "setbudget":
            expenseManager.handleSetBudget(userInput);
            break;
        case "list":
            expenseManager.handleList();
            break;
        case "help":
            expenseManager.handleHelp();
            break;
        case "mark":
            expenseManager.handleMarkUnmark(userInput, true);
            break;
        case "unmark":
            expenseManager.handleMarkUnmark(userInput, false);
            break;
        default:
            ui.showUnknownCommand();
            break;
        }
    }
}

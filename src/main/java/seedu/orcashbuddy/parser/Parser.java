package seedu.orcashbuddy.parser;

import seedu.orcashbuddy.command.*;
import seedu.orcashbuddy.exception.OrCashBuddyException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input and creates the appropriate Command objects.
 */
public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private static final int COMMAND_WORD_INDEX = 0;
    private static final int ARGUMENTS_INDEX = 1;
    private static final int MAX_SPLIT_PARTS = 2;

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param userInput the raw input string from the user
     * @return the Command object to be executed
     */
    public Command parse(String userInput) {
        assert userInput != null : "User input must not be null";

        String trimmed = userInput.trim();
        String[] words = trimmed.split("\\s+", MAX_SPLIT_PARTS);
        String commandWord = words[COMMAND_WORD_INDEX].toLowerCase();
        String arguments = (words.length > ARGUMENTS_INDEX) ? words[ARGUMENTS_INDEX] : "";

        LOGGER.fine(() -> "Parsing command: " + commandWord);

        try {
            switch (commandWord) {
            case "add":
                return parseAddCommand(userInput);
            case "setbudget":
                return parseSetBudgetCommand(arguments);
            case "delete":
                return parseDeleteCommand(arguments);
            case "mark":
                return parseMarkCommand(arguments);
            case "unmark":
                return parseUnmarkCommand(arguments);
            case "list":
                return new ListCommand();
            case "help":
                return new HelpCommand();
            default:
                return new InvalidCommand();
            }
        } catch (OrCashBuddyException e) {
            LOGGER.log(Level.WARNING, "Error parsing command: " + e.getMessage(), e);
            return new InvalidParsedCommand(e);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error parsing command: " + e.getMessage(), e);
            return new InvalidCommand();
        }
    }

    //@@author limzerui
    /**
     * Parses the add command and creates an AddCommand object.
     *
     * @param input the full user input string
     * @return an AddCommand object
     * @throws OrCashBuddyException if the input is invalid
     */
    private Command parseAddCommand(String input) throws OrCashBuddyException {
        // Expected format: add a/AMOUNT desc/DESCRIPTION
        String rest = input.length() > 3 ? input.substring(3).trim() : "";

        if (!rest.startsWith("a/")) {
            throw OrCashBuddyException.missingAmountPrefix();
        }

        // Find the start of "desc/" regardless of spacing before it
        int descIdx = rest.indexOf("desc/");
        if (descIdx == -1) {
            throw OrCashBuddyException.missingDescriptionPrefix();
        }

        String amountStr = rest.substring(2, descIdx).trim();
        String description = rest.substring(descIdx + 5).trim();

        if (amountStr.isEmpty()) {
            throw OrCashBuddyException.emptyAmount();
        }
        if (description.isEmpty()) {
            throw OrCashBuddyException.emptyDescription();
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw OrCashBuddyException.invalidAmount(amountStr, e);
        }

        if (!(amount > 0)) {
            throw OrCashBuddyException.amountNotPositive(amountStr);
        }

        LOGGER.log(Level.FINE, "Parsed add command: amount={0}, description={1}",
                new Object[]{amount, description});

        return new AddCommand(amount, description);
    }

    //@@author aydrienlaw
    /**
     * Parses the setbudget command and creates a SetBudgetCommand object.
     *
     * @param arguments the arguments after the command word
     * @return a SetBudgetCommand object
     * @throws OrCashBuddyException if the input is invalid
     */
    private Command parseSetBudgetCommand(String arguments) throws OrCashBuddyException {
        if (arguments.isEmpty()) {
            throw OrCashBuddyException.missingBudgetAmount();
        }

        int amountIdx = arguments.indexOf("a/");
        if (amountIdx == -1) {
            throw OrCashBuddyException.missingAmountPrefix();
        }

        String amountStr = arguments.substring(amountIdx + 2).trim();
        if (amountStr.isEmpty()) {
            throw OrCashBuddyException.emptyAmount();
        }

        double budget;
        try {
            budget = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw OrCashBuddyException.invalidAmount(amountStr, e);
        }

        if (!(budget > 0)) {
            throw OrCashBuddyException.amountNotPositive(amountStr);
        }

        return new SetBudgetCommand(budget);
    }

    //@@author saheer17
    private Command parseDeleteCommand(String arguments) throws OrCashBuddyException {
        int index = parseIndex(arguments, "delete");
        return new DeleteCommand(index);
    }

    //@author muadzyamani
    private Command parseMarkCommand(String arguments) throws OrCashBuddyException {
        int index = parseIndex(arguments, "mark");
        return new MarkCommand(index);
    }

    private Command parseUnmarkCommand(String arguments) throws OrCashBuddyException {
        int index = parseIndex(arguments, "unmark");
        return new UnmarkCommand(index);
    }

    //@@author aydrienlaw
    // Common parsing logic in one place
    private int parseIndex(String arguments, String commandName) throws OrCashBuddyException {
        if (arguments.isEmpty()) {
            throw OrCashBuddyException.missingExpenseIndex(commandName);
        }
        int index;

        try {
            index = Integer.parseInt(arguments.trim());
        } catch (NumberFormatException e) {
            throw OrCashBuddyException.invalidExpenseIndex(e);
        }

        if (index < 1) {
            throw OrCashBuddyException.expenseIndexTooSmall();
        }
        return index;
    }
}
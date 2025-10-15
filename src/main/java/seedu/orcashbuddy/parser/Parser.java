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

    // Command prefixes
    private static final String AMOUNT_PREFIX = "a/";
    private static final String DESCRIPTION_PREFIX = "desc/";

    // Prefix lengths
    private static final int AMOUNT_PREFIX_LENGTH = AMOUNT_PREFIX.length();
    private static final int DESCRIPTION_PREFIX_LENGTH = DESCRIPTION_PREFIX.length();

    // Command lengths
    private static final int ADD_COMMAND_LENGTH = 3;

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
                return parseAddCommand(arguments);
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
            return new InvalidCommand(e);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error parsing command: " + e.getMessage(), e);
            return new InvalidCommand();
        }
    }

    // ========== Command Parsing Methods ==========

    //@@author limzerui
    /**
     * Parses the add command and creates an AddCommand object.
     *
     * @param arguments the arguments after the command word
     * @return an AddCommand object
     * @throws OrCashBuddyException if the input is invalid
     */
    private Command parseAddCommand(String arguments) throws OrCashBuddyException {
        String rest = arguments.length() > ADD_COMMAND_LENGTH ? arguments.substring(ADD_COMMAND_LENGTH).trim() : "";

        double amount = parseAmount(rest, DESCRIPTION_PREFIX);
        String description = parseDescription(rest);

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

        double budget = parseAmount(arguments, null);
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

    // ========== Helper Parsing Methods ==========

    //@@author limzerui
    /**
     * Parses an amount value from input string.
     *
     * @param input the input string containing the amount
     * @param nextPrefix the next prefix after amount, or null if amount is at the end
     * @return the parsed amount as a double
     * @throws OrCashBuddyException if amount is missing, invalid, or non-positive
     */
    private double parseAmount(String input, String nextPrefix)
            throws OrCashBuddyException {
        int amountIdx = input.indexOf(AMOUNT_PREFIX);
        if (amountIdx == -1) {
            throw OrCashBuddyException.missingAmountPrefix();
        }

        String amountStr;
        if (nextPrefix != null) {
            int nextIdx = input.indexOf(nextPrefix);
            if (nextIdx == -1 || nextIdx <= amountIdx) {
                throw new OrCashBuddyException("Amount field must be followed by " + nextPrefix);
            }
            amountStr = input.substring(amountIdx + AMOUNT_PREFIX_LENGTH, nextIdx).trim();
        } else {
            amountStr = input.substring(amountIdx + AMOUNT_PREFIX_LENGTH).trim();
        }
        return validateAmount(amountStr);
    }

    /**
     * Validates and parses an amount string into a double.
     *
     * @param amountStr the string to parse
     * @return the parsed amount
     * @throws OrCashBuddyException if amount is empty, invalid, or non-positive
     */
    private double validateAmount(String amountStr) throws OrCashBuddyException {
        if (amountStr.isEmpty()) {
            throw OrCashBuddyException.emptyAmount();
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

        return amount;
    }

    /**
     * Parses a description from input string.
     *
     * @param input the input string containing the description
     * @return the parsed description
     * @throws OrCashBuddyException if description prefix is missing or description is empty
     */
    private String parseDescription(String input) throws OrCashBuddyException {
        int descIdx = input.indexOf(DESCRIPTION_PREFIX);
        if (descIdx == -1) {
            throw OrCashBuddyException.missingDescriptionPrefix();
        }

        String description = input.substring(descIdx + DESCRIPTION_PREFIX_LENGTH).trim();
        return validateDescription(description);
    }

    /**
     * Validates a description string.
     *
     * @param description the string to validate
     * @return the verified description
     * @throws OrCashBuddyException if description is empty
     */
    private String validateDescription(String description) throws OrCashBuddyException {
        if (description.isEmpty()) {
            throw OrCashBuddyException.emptyDescription();
        }
        return description;
    }

    //@@author aydrienlaw
    /**
     * Parses an expense index from arguments.
     *
     * @param arguments the arguments string containing the index
     * @param commandName the name of the command (for error messages)
     * @return the parsed 1-based index
     * @throws OrCashBuddyException if index is missing, invalid, or less than 1
     */
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

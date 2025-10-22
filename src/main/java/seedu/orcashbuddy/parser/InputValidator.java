package seedu.orcashbuddy.parser;

import seedu.orcashbuddy.exception.OrCashBuddyException;
import seedu.orcashbuddy.expense.Expense;

public class InputValidator {

    //@@author limzerui
    public static double validateAmount(String amountStr, String commandName) throws OrCashBuddyException {
        if (amountStr == null || amountStr.isEmpty()) {
            throw OrCashBuddyException.emptyAmount(commandName);
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw OrCashBuddyException.invalidAmount(amountStr, e);
        }

        if (amount <= 0) {
            throw OrCashBuddyException.amountNotPositive(amountStr);
        }

        return amount;
    }

    public static String validateDescription(String description, String commandName) throws OrCashBuddyException {
        if (description == null || description.trim().isEmpty()) {
            throw OrCashBuddyException.emptyDescription(commandName);
        }
        return description.trim();
    }

    public static String validateCategory(String category, String commandName) throws OrCashBuddyException {
        if (category == null) {
            return Expense.DEFAULT_CATEGORY;
        }

        String trimmed = category.trim();
        if (trimmed.isEmpty()) {
            throw OrCashBuddyException.emptyCategory(commandName);
        }

        // Magic String
        if (!trimmed.matches("[A-Za-z][A-Za-z0-9\\s-]{0,19}")) {
            throw OrCashBuddyException.invalidCategory(trimmed);
        }

        return trimmed;
    }

    //@@author saheer17
    public static int validateIndex(String input, String commandName) throws OrCashBuddyException {
        if (input == null || input.isEmpty()) {
            throw OrCashBuddyException.missingExpenseIndex(commandName);
        }

        try {
            int index = Integer.parseInt(input.trim());
            if (index < 1) {
                throw OrCashBuddyException.expenseIndexTooSmall();
            }
            return index;
        } catch (NumberFormatException e) {
            throw OrCashBuddyException.invalidExpenseIndex(e);
        }
    }
}

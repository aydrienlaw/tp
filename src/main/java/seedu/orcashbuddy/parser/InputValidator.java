package seedu.orcashbuddy.parser;

import seedu.orcashbuddy.exception.OrCashBuddyException;

public class InputValidator {

    //@@author limzerui
    public static double validateAmount(String amountStr) throws OrCashBuddyException {
        if (amountStr == null || amountStr.isEmpty()) {
            throw OrCashBuddyException.emptyAmount();
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

    public static String validateDescription(String description) throws OrCashBuddyException {
        if (description == null || description.trim().isEmpty()) {
            throw OrCashBuddyException.emptyDescription();
        }
        return description.trim();
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

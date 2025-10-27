package seedu.orcashbuddy.parser;

import seedu.orcashbuddy.exception.OrCashBuddyException;

/**
 * Lightweight helper that extracts prefixed argument values from
 * the argument portion of a user command.
 * <p>
 * Example input: {@code "a/50 desc/Lunch cat/Food"}.
 * <ul>
 *   <li>{@code a/} → "50"</li>
 *   <li>{@code desc/} → "Lunch"</li>
 *   <li>{@code cat/} → "Food"</li>
 * </ul>
 *
 * This class does not validate semantics (e.g. positive amounts);
 * validation is handled by {@link InputValidator}. It only slices
 * out raw string segments after known prefixes.
 */
public class ArgumentParser {
    private static final String[] allPrefixes = {"id/","a/", "desc/", "cat/"};
    private final String input;

    /**
     * Creates a new {@code ArgumentParser} for a given argument string.
     *
     * @param input the raw argument substring after the command word
     */
    public ArgumentParser(String input) {
        this.input = input.trim();
    }

    //@@author limzerui
    /**
     * Returns the value associated with a required prefix.
     * For example, calling {@code getValue("a/")} on
     * {@code "a/50 desc/lunch"} returns {@code "50"}.
     *
     * @param prefix the prefix to search for (e.g. {@code "a/"} or {@code "desc/"})
     * @return the value mapped to that prefix, trimmed
     * @throws OrCashBuddyException if the prefix is missing
     */
    public String getValue(String prefix) throws OrCashBuddyException {
        int prefixIndex = input.indexOf(prefix);
        if (prefixIndex == -1) {
            throw new OrCashBuddyException("Missing prefix: " + prefix);
        }

        int nextPrefixIdx = findNextPrefix(prefixIndex);
        String value = (nextPrefixIdx == -1)
                ? input.substring(prefixIndex + prefix.length())
                : input.substring(prefixIndex + prefix.length(), nextPrefixIdx);

        return value.trim();
    }

    /**
     * Returns the value associated with an optional prefix.
     * Behaves like {@link #getValue(String)} except it returns {@code null}
     * if the prefix does not appear.
     *
     * @param prefix the prefix to search for (e.g. {@code "cat/"})
     * @return the associated value (trimmed), or {@code null} if the prefix is absent
     */
    public String getOptionalValue(String prefix) {
        int prefixIndex = input.indexOf(prefix);
        if (prefixIndex == -1) {
            return null;
        }

        int nextPrefixIdx = findNextPrefix(prefixIndex);
        String value = (nextPrefixIdx == -1)
                ? input.substring(prefixIndex + prefix.length())
                : input.substring(prefixIndex + prefix.length(), nextPrefixIdx);

        return value.trim();
    }

    /**
     * Finds the earliest prefix index that appears after {@code currentPrefixIdx}.
     * Used internally to know where one argument value ends and the next begins.
     *
     * @param currentPrefixIdx the index of the current prefix in {@link #input}
     * @return the index of the next prefix, or -1 if none
     */
    private int findNextPrefix(int currentPrefixIdx) {
        int nextIdx = -1;
        for (String prefix : allPrefixes) {
            int idx = input.indexOf(prefix, currentPrefixIdx + 1);
            if (idx != -1 && (nextIdx == -1 || idx < nextIdx)) {
                nextIdx = idx;
            }
        }
        return nextIdx;
    }
}

package seedu.orcashbuddy.parser;

import seedu.orcashbuddy.exception.OrCashBuddyException;

public class ArgumentParser {
    private static final String[] allPrefixes = {"a/", "desc/", "cat/"};
    private final String input;

    public ArgumentParser(String input) {
        this.input = input.trim();
    }

    //@@author limzerui
    /**
     * Returns the value associated with a prefix.
     * Example: getValue("a/") from "a/50 desc/lunch" → "50"
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
     * Returns the value associated with a prefix, or null if not present.
     *
     * @param prefix the prefix to search for
     * @return the associated value, or null if prefix absent
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

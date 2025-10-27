# Project: orCASHbuddy

## Overview

orCASHbuddy is a Java 17 command-line application that helps students track expenses against a lightweight budget without the overhead of spreadsheets. It provides a fast, distraction-free way to log expenses, manage a simple budget, and gain quick insights into spending habits, all through an intuitive command-line interface.

## Summary of Contributions

*   **Code contributed:** [Link to tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=saheer17&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

*   **Contributions to the UG:** Documented sort and delete command usage and explained the application’s storage system in the User Guide.

*   **Contributions to the DG:** 
    *   Documented the sort and delete functionalities, explaining their operation in detail. Added `delete-sequence.puml` and `sort-sequence.puml` diagrams to illustrate the sequence of each command. 
    *   Provided an overview of the storage component in the Design subsection and created the `storage-component-class.puml` class diagram to visually represent its structure. Delivered an in-depth explanation of the storage management implementation in the Implementation subsection and included the `storage-manager-sequence.puml` sequence diagram to support understanding.

*   **Enhancements implemented:**
    *   **`delete` Feature Implementation:**
        *   **Functionality:** Developed the core `delete` command to allow users to remove an expense entry by its index. This feature provides essential expense management capabilities, ensuring users can maintain an accurate and up-to-date expense list.
        *   **Class Contributions:**
            *   `Parser.java`: Implemented the parseDeleteCommand method to interpret user input for the delete command, validate the provided index and instantiate a DeleteCommand object.
            *   `InputValidator.java`: Added the `validateIndex` method to validate that an expense index is a positive integer and within range, throwing specific OrCashBuddyException instances for invalid or missing indexes.
            *   `DeleteCommand.java`: Developed the `execute` method to remove the specified expense from the `ExpenseManager`, display confirmation via the `Ui` and update the budget status accordingly.
            *   `ExpenseManager.java`: Implemented the `deleteExpense` method to handle deletion logic, including index validation and budget rebalancing if a marked expense is removed. Integrated detailed logging and assertion checks for data consistency.
            *   `Ui.java`: Contributed to displaying user feedback for deleted expense via `showDeletedExpense`.
        *   **Exceptions Added:** `OrCashBuddyException`: Introduced new exception cases including `missingExpenseIndex` and `invalidExpenseIndex` for robust error handling in invalid deletion scenarios. These exceptions are used by other commands as well.
        *   **Testing:** Developed comprehensive test cases and code for the `delete` command, covering various scenarios including valid inputs, index out of range and deletion of marked expense.
        
    *   **`sort` Feature Implementation:**
        *   **Functionality:** Implemented the `sort` command to organise all expenses in descending order by their amount. This allows users to easily identify their highest expenses and manage budgets more effectively.
        *   **Class Contributions:**
            *   `Parser.java`: Added the `parseSortCommand` method to recognise the `sort` keyword, validate the absence of arguments and create a `SortCommand` object.
            *   `SortCommand.java`: Implemented the `execute` method to call `ExpenseManager.sortExpenses()`, handle empty-list cases gracefully and delegate display responsibilities to the `Ui`.
            *   `ExpenseManager.java`: Implemented the `sortExpenses` method, which generates a new list sorted by expense amount in descending order, maintaining immutability of the original list.
            *   `Ui.java`: Added helper methods (`showSortedExpenseList`) to clearly display sorted expenses and empty list messages to users.
        *   **Testing:** Implemented tests to confirm correct sorting behavior, empty-list handling and UI output formatting.
        
    *   **`Storage` Feature Implementation:**
        *   **Functionality:** Designed and implemented persistent data storage via the `StorageManager` class. This ensures that user expenses and budgets are automatically saved and reloaded across application sessions.
        *   **Class Contributions:**
            *   `StorageManager.java`: Fully developed file handling operations for saving (`saveExpenseManager`) and loading (`loadExpenseManager`) serialized ExpenseManager objects. Ensured folder/file creation, handled I/O exceptions and integrated logging for diagnostics.
            *   `ExpenseManager.java`: Confirmed compatibility with Java serialization by implementing Serializable and defining a stable internal state for persistence.
            *   `Ui.java`: Contributed `showError` methods for user-facing feedback when storage-related issues occur, such as permission denied or corrupted file.
        *   **Exceptions Added:** Implemented user-facing error handling for `IOException` and `SecurityException` cases, ensuring resilience and recovery (e.g. starting with a new empty expense manager when loading fails).
        *   **Testing:** Verified that expenses persist correctly across application runs, including scenarios for missing files.
        
*   **Contributions to team-based tasks:**
    *   Created and formatted a shared Excel sheet to collaboratively manage and track User Stories by category.
    *   Maintained the issue tracker.

*   **Review/mentoring contributions:**
    *   Reviewed and mentored teammates on feature design and implementation, e.g. pull request [#55](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/55), where I provided feedback on considering edge cases for mark/unmark command and refactoring certain logic.
    *   Provided consistent support during team meetings and online discussions to clarify requirements and maintain shared understanding of objectives.
    *   Provided constructive suggestions to improve code refactorability and proposed effective solutions for identified bugs.



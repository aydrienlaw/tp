# Project: orCASHbuddy

## Overview

orCASHbuddy is a Java 17 command-line application that helps students track expenses against a lightweight budget without the overhead of spreadsheets. It provides a fast, distraction-free way to log expenses, manage a simple budget, and gain quick insights into spending habits, all through an intuitive command-line interface.

## Summary of Contributions

*   **Code contributed:** [Link to tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=limzerui&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=limzerui&tabRepo=AY2526S1-CS2113-T11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

*   **Enhancements implemented:**
    *   **`add` Feature Implementation:**
        *   **Functionality:** Developed the core `add` command, enabling users to record expenses with a specified amount (`a/AMOUNT`), description (`desc/DESCRIPTION`), and an optional category (`cat/CATEGORY`). This feature is crucial for tracking spending and forms the backbone of the application's data input.
        *   **Class Contributions:**
            *   `Parser.java`: Implemented the `parseAddCommand` method to correctly interpret user input for adding expenses, delegating argument extraction and validation.
            *   `ArgumentParser.java`: Developed methods (`getValue`, `getOptionalValue`) to robustly extract prefix-based arguments (e.g., `a/`, `desc/`, `cat/`) from raw user input, handling both required and optional parameters.
            *   `InputValidator.java`: Created static methods (`validateAmount`, `validateDescription`, `validateCategory`) to ensure the correctness and format of expense details, throwing `OrCashBuddyException` for invalid inputs to maintain data integrity.
            *   `AddCommand.java`: Implemented the `execute` method to encapsulate the logic for creating an `Expense` object and adding it to the `ExpenseManager`.
            *   `Expense.java`: Defined the structure of an expense, including amount, description, category, and paid status, along with its display formatting (`formatForDisplay`).
            *   `ExpenseManager.java`: Implemented the `addExpense` method to manage the collection of expenses, ensuring new expenses are correctly integrated.
            *   `Ui.java`: Contributed to displaying user feedback for new expenses (`showNewExpense`). 
        *   **Testing:** Developed comprehensive test cases and code for the `add` command, covering various scenarios including valid inputs, missing required prefixes, and invalid data formats.

    *   **`bye` Feature Implementation:**
        *   **Functionality:** Did up the `bye` command for graceful application termination, ensuring data persistence and a clean exit.
        *   **Contributions:**
            *   `Parser.java`: Implemented `parseByeCommand` to handle command recognition and unexpected arguments.
            *   `ByeCommand.java`: Developed the `execute` method to orchestrate the farewell message display and signal application shutdown.
            *   `Ui.java`: Contributed `showGoodbye` for the farewell message and `showSeparator` for consistent formatting.
        *   **Testing:** Authored tests to validate the `bye` command's execution and proper exit signaling.

    *   **Testing Contributions (General):**
        *   Developed testing cases and code for core components including `Ui` (ensuring correct display of messages), `InputValidator` (comprehensive validation scenarios), `Parser` (correct command parsing), `InvalidCommandTest` (handling of invalid commands), `UnmarkCommandTest` (verifying unmarking logic), and `ArgumentParserTest`. 

*   **Contributions to the User Guide:**
    *   Wrote the documentation for the `add` and `bye` features.
    *   Added the Command Summary section.
    *   Wrote the FAQ section.

*   **Contributions to the Developer Guide:**
    *   Set up the initial skeleton of the Developer Guide.
    *   Wrote the documentation for the `add`, `bye`, and `help` features, including their respective sequence diagrams.
    *   Wrote the full appendix, from Appendix A to Appendix E.

*   **Contributions to team-based tasks:**
    *   Provided skeleton code for the initial beginning of the project.
    *   Contributed to the design and creation of the skeleton and template for sequence diagrams used in the documentation.
    *   Maintained the issue tracker.

*   **Review/mentoring contributions:**
    *   Reviewed and mentored teammates on feature design and implementation, e.g. pull request [#54](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/54), where I provided feedback on command parsing and validation logic to ensure consistency with project conventions.
    *   Actively coordinated and supported the team during in-person meetings and Telegram discussions—clarifying requirements, unblocking tasks, and maintaining alignment on goals.


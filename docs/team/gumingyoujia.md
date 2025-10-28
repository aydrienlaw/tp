# Project: orCASHbuddy

## Overview

**orCASHbuddy** is a Java 17 command-line application designed for **Residential College 4 (RC4) Interest Group treasurers** to efficiently track and manage their group’s expenses and budgets. It provides a lightweight, spreadsheet-free way to log expenses, monitor budget balances, and visualize financial health — all through a simple and reliable text-based interface.

---

## Summary of Contributions

### Code Contributed
[Click here for full breakdown on tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=gumingyoujia&breakdown=true)

---

### Enhancements Implemented

#### 1. `edit` Feature Implementation
- **Functionality:**  
  Enables users to modify the amount, description, or category of an existing expense. If the expense was previously marked as paid, the marked status is preserved after editing.  
  Displays feedback through the UI and recalculates the remaining balance automatically.

- **Justification:**  
  This feature is crucial for user flexibility — treasurers may occasionally enter incorrect expense details. Allowing edits ensures data accuracy and usability without deleting and re-adding expenses.

- **Class Contributions:**
    - `Parser.java`: Implemented `parseEditCommand()` to extract arguments from user input and create the appropriate `EditCommand` object.
    - `EditCommand.java`: Implemented the `execute()` method to handle editing logic:
        - Retrieves the original expense via `ExpenseManager.getExpense()`.
        - Creates a new `Expense` with updated fields.
        - Preserves mark/unmark status.
        - Calls `Ui.showEditedExpense()` or `Ui.showEmptyEdit()` if no changes are made.
    - `ExpenseManager.java`: Implemented `replaceExpense()` to replace old entries with edited ones while maintaining data integrity.
    - `Ui.java`: Implemented `showEditedExpense()` and `showEmptyEdit()` for improved user feedback after editing.


---

#### 2. `list` Feature Implementation
- **Functionality:**  
  Displays all current expenses with their marked/unmarked status, total budget, total spent, and remaining balance.

- **Justification:**  
  This feature provides treasurers with a quick summary of the club’s financial situation and is essential for locating expense indices used in other commands (e.g., `edit`, `delete`, `mark`).

- **Detailed Breakdown:**
    - Integrated automatic recalculation of total expenses and remaining balance after each `add`, `edit`, `mark`, or `delete` operation.

- **Testing:**  
  Developed and verified outputs for empty lists, populated lists, and real-time budget updates.

---

#### 3. `help` Feature Implementation
- **Functionality:**  
  Displays a neatly formatted list of all available commands and their correct usage formats when users enter `help`.

- **Justification:**  
  Acts as a quick reference for treasurers, minimizing onboarding friction and command syntax errors.

- **Class Contributions:**
    - `HelpCommand.java`: Implemented `execute()` to print the command reference by calling `Ui.showMenu()`.
    - `Ui.java`: Implemented `showMenu()` to present a comprehensive command summary and usage formats.


---

#### 4. Budget Alert Feature
- **Functionality:**  
  Displays visual alerts when the remaining balance approaches or exceeds budget thresholds:
    - **NEAR:** Remaining balance < $10
    - **EQUAL:** Remaining balance = $0
    - **EXCEEDED:** Remaining balance < $0

- **Justification:**  
  Ensures RC4 IG treasurers are aware of potential overspending, improving accountability and financial control.


- **Testing:**  
  Validated alert triggers for each threshold scenario.

---

### Testing Contributions (General)

- Authored test cases for:
    - `EditCommandTest`
    - `HelpCommandTest`
    - `ListCommandTest`
    - `AlertFeatureTest`
- Covered edge cases such as invalid inputs, empty data states, and no-change edits.

---

### Documentation Contributions

#### User Guide
- Authored and refined sections for:
    - `edit`, `list`, and alert features ([PR #96](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/96))
- Contextualized examples to the **RC4 IG Treasurer** use case.
- Added visuals and screenshots ([PR #158](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/158)).
- Unified example formatting for professionalism.
- Contributed to the overall structure and consistency of the UG.

#### Developer Guide
- Added implementation details and UML sequence diagrams for:
    - `edit` → `docs/diagrams/edit-sequence.puml`
    - `list` → `docs/diagrams/list-sequence.puml`
    - `help` → `docs/diagrams/help-sequence.puml`
- Wrote rationale and control flow explanations for each feature ([PR #96](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/96)) ([PR #157](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/157)).

---

### Project Management and Team Contributions

- Actively facilitated initial project ideation.
- Created the user story spreadsheet.
- Maintained the issue tracker.
- Reviewed pull requests and provided feedback (e.g., [PR #54](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/54)).
- Improved`OrCashBuddyException` `InvalidCommand` and `validateIndex` methods for clearer error messaging across commands.





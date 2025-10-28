## Project: orCASHbuddy

### Overview
**orCASHbuddy** is a Java 17 CLI app built for **RC4 Interest Group treasurers** to track and manage budgets efficiently. It offers a spreadsheet-free way to log expenses, monitor balances, and visualize financial health through a simple text-based interface.

---

### Code Contributed
[View full breakdown on tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=gumingyoujia&breakdown=true)

---

### Enhancements Implemented

#### 1. `edit` Feature
- **Functionality:** Allows modification of an expense’s amount, description, or category while preserving mark status. Automatically updates remaining balance and displays UI feedback.
- **Justification:** Enables data accuracy and flexibility without deleting and re-adding entries.
- **Main Contributions:**
  - `Parser.java`: Added `parseEditCommand()`.
  - `EditCommand.java`: Implemented `execute()` logic for editing and feedback.
  - `ExpenseManager.java`: Added `replaceExpense()`.
  - `Ui.java`: Added `showEditedExpense()` and `showEmptyEdit()`.

---

#### 2. `list` Feature
- **Functionality:** Displays all expenses with mark status, total budget, spent amount, and remaining balance.
- **Justification:** Provides a quick financial summary and reference for other commands (`edit`, `delete`, `mark`).
- **Details:** Automatically recalculates totals after updates.
- **Testing:** Verified outputs for empty, populated, and updated lists.

---

#### 3. `help` Feature
- **Functionality:** Shows all available commands and usage formats when `help` is entered.
- **Justification:** Reduces user errors and onboarding friction.
- **Main Contributions:**
  - `HelpCommand.java`: Implemented `execute()` calling `Ui.showMenu()`.
  - `Ui.java`: Added `showMenu()` for formatted command reference.

---

#### 4. Budget Alert
- **Functionality:** Displays alerts when balance nears or exceeds thresholds:
  - **NEAR:** < $10
  - **EQUAL:** = $0
  - **EXCEEDED:** < $0
- **Justification:** Promotes spending awareness and accountability.
- **Testing:** Validated all alert triggers.

---

### Testing Contributions
Authored test cases for:
- `EditCommandTest`
- `HelpCommandTest`
- `ListCommandTest`
- `AlertFeatureTest`

Covered edge cases such as invalid inputs, empty data, and unchanged edits.

---

### Documentation Contributions

#### User Guide
- Wrote and refined sections for `edit`, `list`, and alert features ([PR #96](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/96)).
- Added visuals and screenshots ([PR #158](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/158)).
- Standardized formatting and examples tailored to RC4 treasurers.

#### Developer Guide
- Added implementation details and UMLs:
  - `edit` → `docs/diagrams/edit-sequence.puml`
  - `list` → `docs/diagrams/list-sequence.puml`
- Explained rationale and flow for each feature ([PR #96](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/96), [PR #157](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/157)).

---

### Project Management & Team Contributions
- Actively facilitated initial project ideation
- Created user story spreadsheet.
- Managed issue tracker and reviewed PRs (e.g., [PR #54](https://github.com/AY2526S1-CS2113-T11-2/tp/pull/54)).
- Enhanced `OrCashBuddyException`, `InvalidCommand`, and `validateIndex` for clearer error messages.

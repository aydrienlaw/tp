# Developer Guide

This Developer Guide (DG) introduces the internals of **orCASHbuddy**, outlines design decisions, and documents how to extend, test, and deploy the project. It is written for developers who will maintain or enhance the application.

## Acknowledgements

- Command pattern architecture, testing strategy, and documentation structure were adapted from the [AddressBook-Level3](https://se-education.org/addressbook-level3/) (AB3) teaching codebase.
- orCASHbuddy was bootstrapped from the CS2113 template project.

## Table of Contents

1. [Introduction](#introduction)
2. [Setting Up](#setting-up)
3. [Design](#design)
   1. [Overall Architecture](#overall-architecture)
   2. [UI Component](#ui-component)
   3. [Logic Component](#logic-component)
   4. [Model Component](#model-component)
4. [Implementation](#implementation)
   1. [Add Expense Feature](#add-expense-feature)
   2. [Budget Tracking (Mark/Unmark)](#budget-tracking-markunmark)
   3. [Sorting Expenses](#sorting-expenses)
   4. [Graceful Exit](#graceful-exit)
   5. [Future Work](#future-work)
5. [Appendix A: Product Scope](#appendix-a-product-scope)
6. [Appendix B: User Stories](#appendix-b-user-stories)
7. [Appendix C: Non-Functional Requirements](#appendix-c-non-functional-requirements)
8. [Appendix D: Glossary](#appendix-d-glossary)
9. [Appendix E: Instructions for Manual Testing](#appendix-e-instructions-for-manual-testing)

## Introduction

orCASHbuddy is a Java 17 command-line application that helps students track expenses against a lightweight budget without the overhead of spreadsheets. 

## Setting Up

Follow these steps to set up the project in IntelliJ IDEA:

1. Ensure Java 17 is installed and configured as an IntelliJ SDK.
2. Clone the repository and open it as a **Gradle** project.
3. Let Gradle finish downloading dependencies. The main entry point is `seedu.orcashbuddy.Main`.
4. Run `Main#main` once to verify that the welcome banner appears in the Run tool window.
5. Execute `./gradlew test` (or `gradlew.bat test` on Windows) to confirm all JUnit tests pass.

## Design

### Overall Architecture

At runtime, `Main` wires together the `Ui`, `Parser`, and `ExpenseManager` classes. Commands returned by the parser encapsulate user actions and mutate the in-memory state stored in `ExpenseManager`.

The core relationships are captured in `docs/diagrams/architecture-overview.puml` (PlantUML source). Render it with `plantuml docs/diagrams/architecture-overview.puml` when a PNG export is required. The diagram shows:

- `Main` reads user input and delegates to `Parser`.
- `Parser` produces concrete subclasses of the `Command` abstract class.
- Each `Command` manipulates `ExpenseManager` and feeds results to `Ui`.
- `ExpenseManager` owns an `ArrayList<Expense>` plus running totals for budget tracking.

This architecture keeps parsing, business rules, and presentation loosely coupled, simplifying future enhancements such as command history or persistence.

### UI Component

### Logic Component

Namespace: `seedu.orcashbuddy.parser`, `seedu.orcashbuddy.command`

- `Parser` tokenises raw user input, relying on prefix-based arguments (e.g., `a/`, `desc/`, `cat/`).
- Validation is handled by `InputValidator`, keeping the parser lean.
- Each `Command` subclass overrides `execute(ExpenseManager, Ui)` and may throw `OrCashBuddyException` for recoverable errors. Commands signal program termination by overriding `isExit()`.

Sequence for the `add` command (see `docs/diagrams/add-sequence.puml`; render with the same command as above):

1. `Main` receives user input and calls `Parser#parse`.
2. `Parser` returns a new `AddCommand` populated with validated parameters.
3. `Main` invokes `AddCommand#execute`, which persists the `Expense` via `ExpenseManager` and renders feedback through `Ui`.

### Model Component

Namespace: `seedu.orcashbuddy.expense`, `seedu.orcashbuddy.storage`

- `Expense` is an immutable data carrier (amount, description, category, paid state).
- `ExpenseManager` tracks aggregate figures (`budget`, `totalExpenses`, `remainingBalance`) and exposes behaviors consumed by commands (add, delete, mark/unmark, sort).
- There is no persistent storage in v1.0; all data lives in memory until the process exits.

## Implementation

### Add Expense Feature

#### Overview

The add-expense workflow transforms a single line of user input into a populated `Expense` object, updates the in-memory ledger, and provides immediate feedback in the console. We chose a prefix-based syntax (`add a/AMOUNT desc/DESCRIPTION [cat/CATEGORY]`).

#### Control Flow

1. **Input capture:** `Main` reads the raw line and forwards it to `Parser`.
2. **Tokenisation:** `Parser` delegates prefix extraction to `ArgumentParser`. Required prefixes (`a/`, `desc/`) trigger `OrCashBuddyException` if missing, ensuring we fail fast.
3. **Validation:** `InputValidator` converts the amount into a double (rejecting non-positive or malformed numbers), trims the description, and normalises the optional category. Categories must start with an alphabetic character and may include spaces or hyphens; invalid values raise explicit exceptions so `Ui` can present informative error messages.
4. **Command creation:** A new `AddCommand` instance is constructed with the validated primitives. All downstream logic remains immutable; there is no shared mutable state between parser and command.
5. **Execution:** `AddCommand#execute` wraps the primitives into an `Expense`, calls `ExpenseManager#addExpense`, and then defers to `Ui#showNewExpense`.

The sequence diagram in `docs/diagrams/add-sequence.puml` illustrates the interactions between these collaborative components. Rendering the diagram (e.g., `plantuml docs/diagrams/add-sequence.puml`) is recommended when onboarding new contributors.

#### State Mutation and Invariants

`ExpenseManager#addExpense` enforces a set of assertions: the amount must remain positive, the description non-blank, and the category non-empty. Although assertions are disabled at runtime by default, they serve as guardrails during development and test execution (`./gradlew test` runs with `-ea`). The manager appends the expense to an `ArrayList`, deliberately avoiding sorting or filtering to preserve insertion order for index-based commands.

The `Expense` object itself is largely immutable—only the paid flag can change post-construction. We considered using a builder but opted for a simple constructor to keep object creation lightweight. Formatting logic (`Expense#formatForDisplay`) centralises the "[`[X] [CATEGORY] DESCRIPTION - $AMOUNT`]" representation so that both `Ui` and tests stay in sync.

#### Logging and Diagnostics

`AddCommand` records a structured log entry (`amount`, `description`, `category`) at INFO level. Logs are useful when validating manual test transcripts or diagnosing corrupted input. Since categories may contain spaces, logging at command level avoids the need to recompute arguments later.

#### Error Handling Strategy

During parsing, `OrCashBuddyException`s raised by `InputValidator` are caught inside `Parser` and wrapped in an `InvalidCommand`, ensuring `Ui` can display contextual usage guidance without unwinding the call stack. Exceptions that arise later during command execution (for example, index errors in `DeleteCommand`) do propagate back to `Main`, which logs the issue and keeps the loop responsive. Because `AddCommand` performs no catch-all handling, unexpected runtime problems still surface promptly during testing.

#### Extensibility and Future Enhancements

- **Additional prefixes:** Introducing new metadata (e.g., `date/`, `repeat/`) requires changes only in `Parser`, `InputValidator`, and `Expense`—the command execution logic remains untouched.
- **Persistence:** When a storage layer is added, `ExpenseManager#addExpense` becomes the natural integration point for writing to disk while preserving existing interactions.

#### Alternatives Considered

- **Positional arguments:** Rejected because optional fields would force users to remember ordering, increasing input errors.
- **Validation inside `AddCommand`:** We deliberately kept parsing and validation upstream; performing both in the command would dilute separation of concerns and complicate error messaging.

### Graceful Exit

#### Overview

Exiting the application used to depend on `Main` inspecting raw input (checking for `bye`). This tightly coupled the loop to a single keyword and prevented other commands from influencing shutdown behaviour. The redesigned flow delegates responsibility to `ByeCommand`, aligning termination with the rest of the command framework and paving the way for richer exit scenarios (e.g., confirm prompts, autosave).

#### Control Flow

1. **Parsing:** When the user enters `bye`, `Parser` instantly returns a `ByeCommand`. Any trailing arguments result in an `OrCashBuddyException`, protecting against typos such as `bye later`.
2. **Execution:** `Main` calls `command.execute(expenseManager, ui)` without special casing. `ByeCommand` logs a concise INFO message and calls `Ui#showGoodbye`.
3. **Exit signalling:** After execution, `Main` queries `command.isExit()`. Only commands that explicitly override this method return `true`. Once `true`, the run loop terminates cleanly.

The sequence diagram stored at `docs/diagrams/bye-sequence.puml` captures this flow. Rendering it clarifies that no other component interacts with the exit decision, preserving a single exit pathway.

#### Rationale

- **Testability:** Unit tests can now instantiate `ByeCommand` directly, verifying both the farewell message and the exit flag without running the whole application loop (`ByeCommandTest` demonstrates this).
- **Extensibility:** If we later introduce persistence, `ByeCommand` can flush data stores before returning, while non-exit commands remain unaffected.


#### Alternatives Considered

- **Direct system exit:** Calling `System.exit(0)` inside `ByeCommand` was rejected because it would complicate testing and bypass finally blocks or future shutdown hooks.
- **Flag in Main:** Another option was a mutable boolean toggled inside `Main` after recognising `bye`. This still requires special casing and scatters exit logic.

By keeping farewell handling within the command framework, orCASHbuddy maintains a coherent abstraction and prepares for richer lifecycle management in subsequent releases.


## Appendix A: Product Scope

### Target User Profile

- Tech-savvy university students who prefer keyboard-centric workflows.
- Manage their interest group spending (meals, transport, subscriptions).
- Comfortable reading concise CLI output and following prefix-based inputs.

### Value Proposition

orCASHbuddy offers a fast, distraction-free way to log expenses and check their impact on a simple budget. Compared to spreadsheets, setup time is negligible and data entry is optimised for the keyboard.

## Appendix B: User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|
## Appendix C: Non-Functional Requirements
1. The application must run on any system with Java 17 installed (Windows, macOS, Linux).
2. Commands should execute within one second for up to 200 stored expenses.
3. The codebase should pass `./gradlew checkstyleMain` with no warnings.
4. The CLI should remain readable on terminals with at least 80 characters width.
5. The project should provide at least 80% branch coverage for command logic tests.

## Appendix D: Glossary

- **Command Prefix:** A short label (e.g., `a/`) that identifies the parameter being provided.
- **Expense Index:** One-based position of an expense in the list output.
- **Paid Expense:** An expense that has been marked as settled using the `mark` command.
- **Uncategorized:** Default category assigned when the user omits the optional `cat/` parameter.

## Appendix E: Instructions for Manual Testing

All tests assume the repository has been cloned and Java 17 is available.

1. **Launch**: Run `./gradlew run` (or `gradlew.bat run`). Confirm the welcome banner and menu appear.
2. **Add Expense**: Enter `add a/4.50 desc/Coffee cat/Drinks`. Expect a “New Expense” confirmation with `[ ] [Drinks] Coffee - $4.50`.
3. **List Expenses**: Enter `list`. Confirm the expense appears and the budget defaults to `$0.00`.
4. **Set Budget**: Enter `setbudget a/50`. Run `list` again to verify the budget and remaining balance display `$50.00`.
5. **Mark Expense**: Enter `mark 1`. `list` should now show `[X] [Drinks]` and total expenses `$4.50` with remaining `$45.50`.
6. **Unmark Expense**: Enter `unmark 1` and verify totals reset to `$0.00` spent.
7. **Sort Expenses**: Add two more entries of varying amounts and run `sort`; check that output is descending by amount.
8. **Delete Expense**: Execute `delete 2` (adjust index if needed) and confirm the list shrinks accordingly.
9. **Exit**: Finish with `bye` and ensure the farewell message is printed.
10. **Regression Script**: The Windows batch script `text-ui-test/runtest.bat` (or `text-ui-test/runtest.sh` on macOS/Linux) rebuilds the JAR and exercises the help command. Ensure `EXPECTED.TXT` matches the actual output before committing changes to commands or messages.

When verifying bug fixes or new features, prefer updating both JUnit tests in `src/test/java/seedu/orcashbuddy` and text UI expectations in `text-ui-test` to prevent regressions.

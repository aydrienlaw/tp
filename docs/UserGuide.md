# orCASHbuddy User Guide

Welcome to the user guide for **orCASHbuddy**, a lightweight command-line assistant for tracking day-to-day expenses and monitoring how they affect your budget. This document explains how to set up the application, describes every available command, and provides quick references for day-to-day use.

## Quick Start

1. **Install Java 17.** orCASHbuddy requires Java 17 or later. You can verify your version by running `java -version` in your terminal.
2. **Download the application.** Grab the latest `orcashbuddy.jar` from your course release or the project’s GitHub Releases page and place it in a convenient folder.
3. **Launch the program.** Open a terminal in the folder containing the JAR and run `java -jar orcashbuddy.jar`.
4. **Try a command.** After the welcome banner appears, type `help` and press Enter to see the list of supported commands.

The application stores data only in memory; closing the program will clear all recorded expenses. Keep the terminal window open for the duration of your session.

## Using This Guide

- Command keywords (e.g., `add`, `list`) are **case-insensitive**.
- Words in `UPPER_SNAKE_CASE` are placeholders you replace with your own values.
- Brackets like `[cat/CATEGORY]` indicate optional parameters. Do not type the brackets themselves.
- Amounts must be positive decimal numbers. Invalid inputs prompt an informative error message.
- Expense indices shown in command examples are **1-based** and correspond to the order displayed by the `list` command.

## Features

### Viewing Help: `help`
Displays the command cheat sheet in the terminal.

**Format:** `help`

Use this whenever you need a quick reminder of the supported commands and their prefixes.

### Adding an Expense: `add`
Creates a new expense entry with an amount, description, and optional category.

**Format:** `add a/AMOUNT desc/DESCRIPTION [cat/CATEGORY]`

**Parameters:**
- `AMOUNT` — Positive number up to two decimal places (e.g., `12.50`).
- `DESCRIPTION` — Short sentence describing the expense.
- `CATEGORY` (optional) — Tag to group similar expenses. It must start with a letter and can include letters, numbers, spaces, or hyphens (maximum 20 characters). If omitted, the category defaults to `Uncategorized`.

**Examples:**
- `add a/4.50 desc/Morning coffee`
- `add a/32.90 desc/Weekly groceries cat/Household`

The newly added expense appears as `[ ] [CATEGORY] DESCRIPTION - $AMOUNT`. The `[ ]` will switch to `[X]` once the expense is marked as paid.

### Setting a Budget: `setbudget`
Defines your total spending budget so orCASHbuddy can compute remaining balance.

**Format:** `setbudget a/AMOUNT`

Setting a new budget overwrites the previous one. The application recalculates your remaining balance whenever you mark or unmark expenses.

**Example:** `setbudget a/500`

### Listing Expenses and Summary: `list`
Shows your current budget, total paid expenses, remaining balance, and every recorded expense in the order added.

**Format:** `list`

If you have no expenses yet, orCASHbuddy tells you so and suggests adding one with the `add` command.

### Marking an Expense as Paid: `mark`
Flags an expense as settled. Marked expenses contribute to the “total expenses” value displayed by `list`.

**Format:** `mark INDEX`

**Example:** `mark 2`

The index refers to the numbering shown by the most recent `list`. Once marked, the display changes to `[X] [CATEGORY] DESCRIPTION - $AMOUNT`, and the amount counts toward your budget usage.

### Unmarking an Expense: `unmark`
Reverts a previously marked expense to unpaid, increasing your remaining balance accordingly.

**Format:** `unmark INDEX`

**Example:** `unmark 2`

### Sorting Expenses by Amount: `sort`
Displays all expenses in descending order of amount (largest first). Sorting does not change the original order used by other commands.

**Format:** `sort`

If there are no expenses to sort, orCASHbuddy will remind you that there are no expenses added yet.

### Deleting an Expense: `delete`
Removes an expense permanently (there is no undo). The application rebalances your budget totals automatically.

**Format:** `delete INDEX`

**Example:** `delete 1`

After deletion, use `list` to confirm the remaining expenses and updated totals.

### Exiting the Application: `bye`
Closes orCASHbuddy gracefully.

**Format:** `bye`

You can also terminate the program by closing the terminal window, but using `bye` ensures the farewell message is displayed.

### Saving and Loading Data

The application automatically saves all your expenses and budget.
Your data is stored automatically after every change and reloaded when you reopen the app, allowing you to continue from where you left off without manually saving or loading anything.

## FAQ

**Q:** What happens if I mistype a command?

**A:** orCASHbuddy prints an error message describing what went wrong (e.g., missing prefixes or invalid numbers). Check the format in the `help` output and try again.

## Command Summary

| Action | Format | Example |
| --- | --- | --- |
| View help | `help` | `help` |
| Add expense | `add a/AMOUNT desc/DESCRIPTION [cat/CATEGORY]` | `add a/19.99 desc/Notebook cat/School` |
| Set budget | `setbudget a/AMOUNT` | `setbudget a/250` |
| List summary | `list` | `list` |
| Mark as paid | `mark INDEX` | `mark 3` |
| Unmark expense | `unmark INDEX` | `unmark 3` |
| Delete expense | `delete INDEX` | `delete 2` |
| Sort expenses | `sort` | `sort` |
| Exit | `bye` | `bye` |

Refer back to the feature sections above for detailed explanations, parameter notes, and expected outcomes.

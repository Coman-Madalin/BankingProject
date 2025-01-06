##### Copyright 2024 Andrei-Madalin Coman (comanandreimadalin@gmail.com)

# Banking Framework

## Overview

This project is a Java-based framework for simulating and managing banking operations. It supports various commands for
account and card management, transactions, and user-related actions. The framework processes JSON input files describing
operations and generates JSON output files reflecting the results.

Design Patterns:
- `Singleton` for `Input` class.
- `Command` for all the commands.
---

## Project Structure

### Source Code (`/src/main/java/org/poo`)

- **Commands**:
    - **BaseCommand**: Abstract class for all commands.
    - **Specific Commands**: Classes implementing various operations, such as:
        - `AddAccount`, `AddFunds`, `SendMoney`, `DeleteAccount`.
        - Utility commands like `PrintTransactions`, `PrintUsers`, and `Report`.

- **Exchange**:
    - Implements currency exchange functionalities (`Exchange`).

- **Input**:
    - Classes for handling input data (`Exchanges`, `Input`, `Users`).

- **Json**:
    - Custom serializers and deserializers:
        - Serializers: `AccountSerializer`, `CardSerializer`, etc.
        - Deserializers: `InputDeserializer`.

- **Transactions**:
    - **BaseTransaction**: Abstract class for all transactions.
    - **Specific Transactions**: Classes implementing various transaction types, such as:
        - `CardActionTransaction`, `PaymentTransaction`, `TransferTransaction`.

- **User**:
    - User-related entities:
        - `User`: Represents a user.
        - `Card`: Represents a user card.
        - `Account`: Represents a user account.

### Entry Points

- **`Main.java`**:
    - Main class to run the entire application.
- **`Test.java`**:
    - Utility for running single test cases.

---

## Extending the Framework

### Adding New Commands

1. Navigate to `command/specific`.
2. Create a new class that extends `BaseCommand`.
3. Implement the command logic.
4. Register the command in the relevant maps in `JsonUtils` and `BaseCommandTypeAdapter`.

### Adding New Transactions

1. Navigate to `transactions/specific`.
2. Create a new class that extends `BaseTransaction`.
3. Implement the transaction logic.

- NOTE: You only need to create a new transaction if you want something to be added to the transaction history of that
  account, which will be printed only when one of three specific commands is given. If you just want to know that it
  executes, `BaseTransaction` is good enough.

---

## How to Run

### Prerequisites

- Java Development Kit (Minimum JDK 21).
- JSON input files as described below.

### Running the Project

1. Place input JSON files in the `./input` directory.
2. Run `Main.java` to process all input files and generate output.
3. Use `Test.java` to process a single input file and output to `out.txt`.

### JSON Input Structure

```
ROOT
├── users (Array)
│   ├── User (Object)
│   │   ├── id (String)
│   │   ├── name (String)
│   │   ├── accounts (Array of Objects)
│   │   ├── cards (Array of Objects)
│   └──  (Repeat for other users)
├── exchangeRates (Array, Optional)
│   ├── Exchange (Object)
│   │   ├── from (String)
│   │   ├── to (String)
│   │   └── rate (Number)
│   └── (Repeat for other exchangeRates)
└── commands (Array)
    ├── Command (Object)
    │   ├── command (String)
    │   ├── timestamp (Number)
    │   └── <arguments> (Optional)
    └── (Repeat for other commands)
```

---

## Notes

1. This framework is designed for processing of predefined actions and is not interactive.

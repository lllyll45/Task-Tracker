# Task Tracker CLI

A simple command-line interface (CLI) application to track and manage your to-do list, built according to the [Task Tracker project](https://roadmap.sh/projects/task-tracker) on roadmap.sh.

This project helps you practice working with the filesystem, handling user inputs, and building a CLI application using only the native Java libraries.


This application allows you to:

- **Add**, **Update**, and **Delete** tasks.
- **Mark** a task as `in-progress` or `done`.
- **List** all tasks.
- **List** all tasks that are `done`.
- **List** all tasks that are `todo` (not done).
- **List** all tasks that are `in-progress`.

All tasks are stored persistently in a JSON file.

## Requirements

- Java 8 or higher
- Command-line interface (Terminal, Command Prompt, etc.)

## Installation and Setup

### 1. Clone or Download the Project

Ensure you have the following Java source files in your project directory:
- `TaskCLI.java`
- `Task.java`

### 2. Compile the Application

Open your terminal in the project directory and run the following command to compile both files:

```bash

javac TaskCLI.java Task.java

```

### 3. Run the Application

```bash

java TaskCLI <command> [arguments]

```

### Usage Examples

```bash

# Add a new task

java TaskCLI add "Buy groceries"

# Update a task

java TaskCLI update 1 "Buy groceries and milk"

# Delete a task

java TaskCLI delete 1

# Mark as in progress

java TaskCLI mark-in-progress 1

# Mark as done

java TaskCLI mark-done 1

# List all tasks

java TaskCLI list

# List only done tasks

java TaskCLI list done

# List only in-progress tasks

java TaskCLI list in-progress

# List only todo tasks

java TaskCLI list todo

```

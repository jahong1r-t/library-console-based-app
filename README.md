# Library Application (Console-based Java Application)

## Project Overview

**This project is for learning purposes, focusing on Java programming and console application development.**

LibraryApplication is a console-based Java application designed to manage a library system. It allows users to log in, borrow books, return books, and view available books. Additionally, the application includes a notification system using `ScheduledExecutorService` to remind users about due books and other important updates.

## GitHub Repository

Repository Link: [Library Console-Based App](https://github.com/jahong1r-t/library-console-based-app.git)

## Prerequisites

To run this application, ensure you have the following installed:

- Java Development Kit (JDK 17 or JDK 8)
- A terminal or command prompt

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/jahong1r-t/library-console-based-app.git
   ```
2. Navigate to the project directory:
   ```sh
   cd LibraryApplication
   ```
3. Compile all Java files into the `out/` directory:
   ```sh
   javac -d out src/*.java
   ```
4. Run the application:
   ```sh
   java -cp out Main
   ```

## Usage

To use the application, log in with one of the default users:

### Default Users

| Role  | Name  | Surname | Login                                      | Password |
| ----- | ----- | ------- | ------------------------------------------ | -------- |
| ADMIN | admin | admin   | admin                                      | admin    |
| USER  | John  | Doe     | 1                                          | 1        |
| USER  | Bob   | Smith   | 2                                          | 2        |
| USER  | Alice | Smith   | [alice@gmail.com](mailto:alice@gmail.com) | alice123 |
| USER  | Bob   | Brown   | [bob@gmail.com](mailto:bob@gmail.com)     | bob123   |
| USER  | Emma  | Johnson | [emma@gmail.com](mailto:emma@gmail.com)   | emma123  |

## Features

- User authentication (Admin & User roles)
- Borrowing and returning books
- Viewing available books
- Managing library records (Admins only)
- **Automated Notifications**: The system uses `ScheduledExecutorService` to periodically send reminders about due books.

## Contributing

If you want to contribute, please fork the repository and submit a pull request.

## License

This project does not have a specific license.


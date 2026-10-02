# Library Management System

## Project Overview

Library Management System is a Java desktop application developed to manage
books, readers, and book issue/return transactions.

The application uses Java Swing for the graphical user interface, JDBC for
database connectivity, and MySQL for storing library data.

## Technologies Used

- Java 21
- Java Swing
- JDBC
- MySQL
- Maven
- IntelliJ IDEA

## Main Features

### Book Management
- Add Book
- View Books
- Search Book
- Update Book
- Delete Book
- Check Book Availability
- View Available Books

### Reader Management
- Add Reader
- View Readers
- Search Reader
- Update Reader
- Delete Reader

### Issue and Return
- Issue Book
- Return Book
- View Issued Books
- Track Issue and Return Status
- Automatic book availability update

### Authentication
- Staff login
- Logout

### Validation
- Required field validation
- Numeric ID validation
- Quantity validation
- Due date validation
- Duplicate email protection
- Error handling

## Project Structure

```text
src/main/java/com.library
├── gui
├── model
├── dao
├── service
├── util
└── Main.java
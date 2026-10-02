CREATE DATABASE IF NOT EXISTS librarymanagementsystem;

USE librarymanagementsystem;

CREATE TABLE IF NOT EXISTS books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    category VARCHAR(50),
    quantity INT NOT NULL,
    available_quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS readers (
    reader_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    address VARCHAR(255),
    registration_date DATE
);

CREATE TABLE IF NOT EXISTS staff (
    staff_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS issue_transactions (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    reader_id INT NOT NULL,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'Issued',

    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (reader_id) REFERENCES readers(reader_id)
);

INSERT INTO staff
(username, password, name, email)
VALUES
('admin', 'admin123', 'Library Admin', 'admin@library.com');
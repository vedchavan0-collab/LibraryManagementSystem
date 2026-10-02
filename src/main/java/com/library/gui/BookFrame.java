package com.library.gui;

import com.library.dao.BookDAO;
import com.library.model.Book;

import javax.swing.*;

public class BookFrame extends JFrame {

    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField categoryField;
    private JTextField quantityField;

    private JButton addButton;
    private JButton viewButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton availableButton;
    private JButton checkAvailabilityButton;

    private JTable bookTable;
    private javax.swing.table.DefaultTableModel tableModel;

    public BookFrame() {

        setTitle("Library Management System - Book Management");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        String[] columns = {
                "ID",
                "Title",
                "Author",
                "Publisher",
                "Category",
                "Quantity",
                "Available"
        };

        tableModel = new javax.swing.table.DefaultTableModel(
                columns,
                0
        );

        bookTable = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(bookTable);

        scrollPane.setPreferredSize(
                new java.awt.Dimension(750, 200)
        );

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(15);

        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(15);

        JLabel publisherLabel = new JLabel("Publisher:");
        publisherField = new JTextField(15);

        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(15);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(15);


        // =========================
        // ADD BOOK
        // =========================

        addButton = new JButton("Add Book");

        addButton.addActionListener(e -> {

            try {

                String title =
                        titleField.getText().trim();

                String author =
                        authorField.getText().trim();

                String publisher =
                        publisherField.getText().trim();

                String category =
                        categoryField.getText().trim();

                String quantityText =
                        quantityField.getText().trim();

                // Check required fields
                if (title.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Title cannot be empty."
                    );

                    return;
                }

                if (author.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Author cannot be empty."
                    );

                    return;
                }

                if (quantityText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Quantity cannot be empty."
                    );

                    return;
                }

                // Convert quantity to number
                int quantity =
                        Integer.parseInt(quantityText);

                // Quantity must be greater than 0
                if (quantity <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Quantity must be greater than 0."
                    );

                    return;
                }

                Book book = new Book(
                        title,
                        author,
                        publisher,
                        category,
                        quantity,
                        quantity
                );

                BookDAO bookDAO =
                        new BookDAO();

                boolean result =
                        bookDAO.addBook(book);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book added successfully!"
                    );

                    // Clear fields after successful addition
                    titleField.setText("");
                    authorField.setText("");
                    publisherField.setText("");
                    categoryField.setText("");
                    quantityField.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to add book."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be a valid number."
                );
            }
        });


        // =========================
        // VIEW BOOKS
        // =========================

        viewButton = new JButton("View Books");

        viewButton.addActionListener(e -> {

            BookDAO bookDAO =
                    new BookDAO();

            java.util.List<Book> books =
                    bookDAO.getAllBooks();

            tableModel.setRowCount(0);

            for (Book book : books) {

                tableModel.addRow(new Object[]{
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getCategory(),
                        book.getQuantity(),
                        book.getAvailableQuantity()
                });
            }
        });


        // =========================
        // SEARCH BOOK
        // =========================

        searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {

            String keyword =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter book title or author:"
                    );

            if (keyword == null ||
                    keyword.trim().isEmpty()) {

                return;
            }

            BookDAO bookDAO =
                    new BookDAO();

            java.util.List<Book> books =
                    bookDAO.searchBooks(
                            keyword.trim()
                    );

            if (books.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No books found."
                );

                return;
            }

            StringBuilder message =
                    new StringBuilder();

            for (Book book : books) {

                message.append("ID: ")
                        .append(book.getBookId())
                        .append("\nTitle: ")
                        .append(book.getTitle())
                        .append("\nAuthor: ")
                        .append(book.getAuthor())
                        .append("\nCategory: ")
                        .append(book.getCategory())
                        .append("\nAvailable: ")
                        .append(book.getAvailableQuantity())
                        .append("\n--------------------\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    message.toString(),
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });


        // =========================
        // UPDATE BOOK
        // =========================

        updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {

            try {

                String idText =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Book ID to update:"
                        );

                if (idText == null ||
                        idText.trim().isEmpty()) {

                    return;
                }

                int bookId =
                        Integer.parseInt(
                                idText.trim()
                        );

                String title =
                        titleField.getText().trim();

                String author =
                        authorField.getText().trim();

                String publisher =
                        publisherField.getText().trim();

                String category =
                        categoryField.getText().trim();

                String quantityText =
                        quantityField.getText().trim();


                // Check required fields

                if (title.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Title cannot be empty."
                    );

                    return;
                }

                if (author.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Author cannot be empty."
                    );

                    return;
                }

                if (quantityText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Quantity cannot be empty."
                    );

                    return;
                }


                // Convert quantity to number

                int quantity =
                        Integer.parseInt(
                                quantityText
                        );


                // Quantity must be greater than 0

                if (quantity <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Quantity must be greater than 0."
                    );

                    return;
                }


                Book book = new Book(
                        title,
                        author,
                        publisher,
                        category,
                        quantity,
                        quantity
                );

                book.setBookId(bookId);

                BookDAO bookDAO =
                        new BookDAO();

                boolean result =
                        bookDAO.updateBook(book);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book updated successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book not found or update failed."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book ID and Quantity must be valid numbers."
                );
            }
        });


        // =========================
        // DELETE BOOK
        // =========================

        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {

            try {

                String idText =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Book ID to delete:"
                        );

                if (idText == null ||
                        idText.trim().isEmpty()) {

                    return;
                }

                int bookId =
                        Integer.parseInt(
                                idText.trim()
                        );

                int confirmation =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to delete Book ID "
                                        + bookId + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                        );

                if (confirmation !=
                        JOptionPane.YES_OPTION) {

                    return;
                }

                BookDAO bookDAO =
                        new BookDAO();

                boolean result =
                        bookDAO.deleteBook(bookId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book deleted successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book not found or delete failed."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book ID must be a number."
                );
            }
        });


        // =========================
        // VIEW AVAILABLE BOOKS
        // =========================

        availableButton =
                new JButton("View Available Books");

        availableButton.addActionListener(e -> {

            BookDAO bookDAO =
                    new BookDAO();

            java.util.List<Book> books =
                    bookDAO.getAvailableBooks();

            tableModel.setRowCount(0);

            for (Book book : books) {

                tableModel.addRow(new Object[]{
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getCategory(),
                        book.getQuantity(),
                        book.getAvailableQuantity()
                });
            }

            if (books.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No books are currently available."
                );
            }
        });


        // =========================
        // CHECK AVAILABILITY
        // =========================

        checkAvailabilityButton =
                new JButton("Check Availability");

        checkAvailabilityButton.addActionListener(e -> {

            String input =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Book ID:"
                    );

            if (input == null) {

                return;
            }

            try {

                int bookId =
                        Integer.parseInt(
                                input.trim()
                        );

                BookDAO bookDAO =
                        new BookDAO();

                int availableQuantity =
                        bookDAO.getAvailableQuantity(
                                bookId
                        );

                if (availableQuantity == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book ID " + bookId +
                                    " does not exist."
                    );

                } else if (availableQuantity == 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book is currently not available."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book is available.\n" +
                                    "Available Quantity: " +
                                    availableQuantity
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid Book ID."
                );
            }
        });


        // =========================
        // ADD COMPONENTS TO PANEL
        // =========================

        panel.add(titleLabel);
        panel.add(titleField);

        panel.add(authorLabel);
        panel.add(authorField);

        panel.add(publisherLabel);
        panel.add(publisherField);

        panel.add(categoryLabel);
        panel.add(categoryField);

        panel.add(quantityLabel);
        panel.add(quantityField);

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(availableButton);
        panel.add(checkAvailabilityButton);

        panel.add(scrollPane);

        add(panel);
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            BookFrame bookFrame =
                    new BookFrame();

            bookFrame.setVisible(true);
        });
    }
}
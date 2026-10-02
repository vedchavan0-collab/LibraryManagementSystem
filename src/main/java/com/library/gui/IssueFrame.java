package com.library.gui;

import com.library.dao.IssueDAO;
import com.library.model.IssueTransaction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class IssueFrame extends JFrame {

    private JTextField bookIdField;
    private JTextField readerIdField;
    private JTextField dueDateField;
    private JTextField returnIssueIdField;

    private JButton issueButton;
    private JButton viewButton;
    private JButton returnButton;

    public IssueFrame() {

        setTitle("Library Management System - Issue / Return Book");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "Issue / Return Book",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // =========================
        // ISSUE BOOK INPUT PANEL
        // =========================

        JPanel inputPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // Book ID
        JLabel bookIdLabel =
                new JLabel("Book ID:");

        bookIdField =
                new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        inputPanel.add(
                bookIdLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        inputPanel.add(
                bookIdField,
                gbc
        );

        // Reader ID
        JLabel readerIdLabel =
                new JLabel("Reader ID:");

        readerIdField =
                new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        inputPanel.add(
                readerIdLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        inputPanel.add(
                readerIdField,
                gbc
        );

        // Due Date
        JLabel dueDateLabel =
                new JLabel(
                        "Due Date (YYYY-MM-DD):"
                );

        dueDateField =
                new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        inputPanel.add(
                dueDateLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        inputPanel.add(
                dueDateField,
                gbc
        );

        mainPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        // =========================
        // ISSUE BOOK BUTTON
        // =========================

        issueButton =
                new JButton("Issue Book");

        issueButton.addActionListener(e -> {

            try {

                int bookId =
                        Integer.parseInt(
                                bookIdField
                                        .getText()
                                        .trim()
                        );

                int readerId =
                        Integer.parseInt(
                                readerIdField
                                        .getText()
                                        .trim()
                        );

                LocalDate dueDate =
                        LocalDate.parse(
                                dueDateField
                                        .getText()
                                        .trim()
                        );

                LocalDate issueDate =
                        LocalDate.now();

                // Due date validation
                if (dueDate.isBefore(issueDate)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Due date cannot be before today's date."
                    );

                    return;
                }

                IssueTransaction transaction =
                        new IssueTransaction(
                                bookId,
                                readerId,
                                issueDate,
                                dueDate,
                                "Issued"
                        );

                IssueDAO issueDAO =
                        new IssueDAO();

                boolean success =
                        issueDAO.issueBook(
                                transaction
                        );

                if (success) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book issued successfully!"
                    );

                    bookIdField.setText("");
                    readerIdField.setText("");
                    dueDateField.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book could not be issued.\n"
                                    + "Check Book ID, Reader ID, "
                                    + "or book availability."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book ID and Reader ID must be valid numbers."
                );

            } catch (DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the due date in YYYY-MM-DD format."
                );
            }
        });

        // =========================
        // VIEW ISSUED BOOKS
        // =========================

        viewButton =
                new JButton("View Issued Books");

        viewButton.addActionListener(e -> {

            IssueDAO issueDAO =
                    new IssueDAO();

            List<String> issuedBooks =
                    issueDAO.getAllIssuedBooks();

            if (issuedBooks.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No issue transactions found."
                );

                return;
            }

            StringBuilder message =
                    new StringBuilder();

            for (String details : issuedBooks) {

                message.append(details)
                        .append("\n");
            }

            JTextArea textArea =
                    new JTextArea(
                            message.toString()
                    );

            textArea.setEditable(false);

            textArea.setFont(
                    new Font(
                            "Monospaced",
                            Font.PLAIN,
                            13
                    )
            );

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            scrollPane.setPreferredSize(
                    new Dimension(500, 300)
            );

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Issued Books",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // =========================
        // REDESIGNED RETURN BOOK SECTION
        // =========================

        JPanel returnPanel =
                new JPanel(new GridBagLayout());

        returnPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Return Book"
                )
        );

        GridBagConstraints returnGbc =
                new GridBagConstraints();

        returnGbc.insets =
                new Insets(8, 8, 8, 8);

        returnGbc.fill =
                GridBagConstraints.HORIZONTAL;

        // Return instruction
        JLabel returnInstructionLabel =
                new JLabel(
                        "Enter the Issue ID of the book you want to return:"
                );

        returnInstructionLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        returnGbc.gridx = 0;
        returnGbc.gridy = 0;
        returnGbc.gridwidth = 2;
        returnGbc.weightx = 1;

        returnPanel.add(
                returnInstructionLabel,
                returnGbc
        );

        // Issue ID label
        JLabel returnIssueIdLabel =
                new JLabel("Issue ID:");

        returnGbc.gridx = 0;
        returnGbc.gridy = 1;
        returnGbc.gridwidth = 1;
        returnGbc.weightx = 0;

        returnPanel.add(
                returnIssueIdLabel,
                returnGbc
        );

        // Issue ID field
        returnIssueIdField =
                new JTextField(15);

        returnGbc.gridx = 1;
        returnGbc.weightx = 1;

        returnPanel.add(
                returnIssueIdField,
                returnGbc
        );

        // Return button
        returnButton =
                new JButton("Return Book");

        returnGbc.gridx = 0;
        returnGbc.gridy = 2;
        returnGbc.gridwidth = 2;
        returnGbc.weightx = 0;

        returnGbc.fill =
                GridBagConstraints.NONE;

        returnGbc.anchor =
                GridBagConstraints.CENTER;

        returnPanel.add(
                returnButton,
                returnGbc
        );

        // =========================
        // RETURN BOOK FUNCTIONALITY
        // =========================

        returnButton.addActionListener(e -> {

            try {

                int issueId =
                        Integer.parseInt(
                                returnIssueIdField
                                        .getText()
                                        .trim()
                        );

                IssueDAO issueDAO =
                        new IssueDAO();

                boolean success =
                        issueDAO.returnBook(
                                issueId
                        );

                if (success) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book returned successfully!"
                    );

                    returnIssueIdField.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book could not be returned.\n"
                                    + "Check the Issue ID or "
                                    + "make sure the book is still issued."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Issue ID must be a valid number."
                );
            }
        });

        // =========================
        // BUTTON AREA
        // =========================

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setLayout(
                new BoxLayout(
                        buttonPanel,
                        BoxLayout.Y_AXIS
                )
        );

        issueButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        viewButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        returnPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Issue button
        buttonPanel.add(
                issueButton
        );

        buttonPanel.add(
                Box.createVerticalStrut(10)
        );

        // View button
        buttonPanel.add(
                viewButton
        );

        buttonPanel.add(
                Box.createVerticalStrut(15)
        );

        // Return section
        buttonPanel.add(
                returnPanel
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // Add main panel
        add(mainPanel);
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            IssueFrame issueFrame =
                    new IssueFrame();

            issueFrame.setVisible(true);
        });
    }
}
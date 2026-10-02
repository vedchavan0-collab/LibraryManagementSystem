package com.library.gui;

import com.library.dao.ReaderDAO;
import com.library.model.Reader;

import javax.swing.*;

public class ReaderFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;

    private JButton addButton;
    private JButton viewButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JTable readerTable;
    private javax.swing.table.DefaultTableModel tableModel;

    public ReaderFrame() {

        setTitle("Library Management System - Reader Management");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        // ================= TABLE =================

        String[] columns = {
                "ID",
                "Name",
                "Email",
                "Phone",
                "Address",
                "Registration Date"
        };

        tableModel = new javax.swing.table.DefaultTableModel(
                columns,
                0
        );

        readerTable = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(readerTable);

        scrollPane.setPreferredSize(
                new java.awt.Dimension(650, 200)
        );

        // ================= FIELDS =================

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(15);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(15);


        // =================================================
        // ADD READER
        // =================================================

        addButton = new JButton("Add Reader");

        addButton.addActionListener(e -> {

            String name =
                    nameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            String address =
                    addressField.getText().trim();

            // Name validation
            if (name.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Name cannot be empty."
                );

                return;
            }

            Reader reader = new Reader(
                    name,
                    email,
                    phone,
                    address,
                    java.time.LocalDate.now()
            );

            ReaderDAO readerDAO =
                    new ReaderDAO();

            boolean result =
                    readerDAO.addReader(reader);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reader added successfully!"
                );

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add reader.\n" +
                                "Email may already be registered."
                );
            }
        });


        // =================================================
        // VIEW READERS
        // =================================================

        viewButton = new JButton("View Readers");

        viewButton.addActionListener(e -> {

            ReaderDAO readerDAO =
                    new ReaderDAO();

            java.util.List<Reader> readers =
                    readerDAO.getAllReaders();

            tableModel.setRowCount(0);

            for (Reader reader : readers) {

                tableModel.addRow(new Object[]{
                        reader.getReaderId(),
                        reader.getName(),
                        reader.getEmail(),
                        reader.getPhone(),
                        reader.getAddress(),
                        reader.getRegistrationDate()
                });
            }
        });


        // =================================================
        // SEARCH READERS
        // =================================================

        searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {

            String keyword =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter reader name, email, or phone:"
                    );

            if (keyword == null ||
                    keyword.trim().isEmpty()) {

                return;
            }

            ReaderDAO readerDAO =
                    new ReaderDAO();

            java.util.List<Reader> readers =
                    readerDAO.searchReaders(
                            keyword.trim()
                    );

            if (readers.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No readers found."
                );

                return;
            }

            StringBuilder message =
                    new StringBuilder();

            for (Reader reader : readers) {

                message.append("ID: ")
                        .append(reader.getReaderId())
                        .append("\nName: ")
                        .append(reader.getName())
                        .append("\nEmail: ")
                        .append(reader.getEmail())
                        .append("\nPhone: ")
                        .append(reader.getPhone())
                        .append("\nAddress: ")
                        .append(reader.getAddress())
                        .append("\nRegistration Date: ")
                        .append(reader.getRegistrationDate())
                        .append("\n--------------------\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    message.toString(),
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });


        // =================================================
        // UPDATE READER
        // =================================================

        updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {

            try {

                String idText =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Reader ID to update:"
                        );

                if (idText == null ||
                        idText.trim().isEmpty()) {

                    return;
                }

                int readerId =
                        Integer.parseInt(
                                idText.trim()
                        );

                String name =
                        nameField.getText().trim();

                String email =
                        emailField.getText().trim();

                String phone =
                        phoneField.getText().trim();

                String address =
                        addressField.getText().trim();

                // Name validation
                if (name.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Name cannot be empty."
                    );

                    return;
                }

                Reader reader = new Reader(
                        name,
                        email,
                        phone,
                        address,
                        java.time.LocalDate.now()
                );

                reader.setReaderId(readerId);

                ReaderDAO readerDAO =
                        new ReaderDAO();

                boolean result =
                        readerDAO.updateReader(reader);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Reader updated successfully!"
                    );

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Reader not found or update failed."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reader ID must be a number."
                );
            }
        });


        // =================================================
        // DELETE READER
        // =================================================

        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {

            try {

                String idText =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Reader ID to delete:"
                        );

                if (idText == null ||
                        idText.trim().isEmpty()) {

                    return;
                }

                int readerId =
                        Integer.parseInt(
                                idText.trim()
                        );

                int confirmation =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to delete Reader ID "
                                        + readerId + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                        );

                if (confirmation !=
                        JOptionPane.YES_OPTION) {

                    return;
                }

                ReaderDAO readerDAO =
                        new ReaderDAO();

                boolean result =
                        readerDAO.deleteReader(readerId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Reader deleted successfully!"
                    );

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Reader not found or delete failed."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reader ID must be a number."
                );
            }
        });


        // =================================================
        // ADD COMPONENTS
        // =================================================

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(phoneLabel);
        panel.add(phoneField);

        panel.add(addressLabel);
        panel.add(addressField);

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        panel.add(scrollPane);

        add(panel);
    }


    // =================================================
    // CLEAR FIELDS
    // =================================================

    private void clearFields() {

        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }


    // =================================================
    // MAIN METHOD
    // =================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ReaderFrame readerFrame =
                    new ReaderFrame();

            readerFrame.setVisible(true);
        });
    }
}
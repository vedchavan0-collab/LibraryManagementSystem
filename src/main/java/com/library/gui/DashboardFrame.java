package com.library.gui;

import com.library.dao.DashboardDAO;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private JButton bookButton;
    private JButton readerButton;
    private JButton issueButton;
    private JButton logoutButton;

    public DashboardFrame(String staffName) {

        setTitle("Library Management System - Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );

        JLabel titleLabel =
                new JLabel("Library Management System");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel =
                new JLabel("Welcome, " + staffName + "!");

        welcomeLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(welcomeLabel);


        // =========================
        // STATISTICS
        // =========================

        DashboardDAO dashboardDAO =
                new DashboardDAO();

        int totalBooks =
                dashboardDAO.getTotalBooks();

        int totalReaders =
                dashboardDAO.getTotalReaders();

        int issuedBooks =
                dashboardDAO.getIssuedBooksCount();

        JPanel statsPanel = new JPanel(
                new GridLayout(1, 3, 10, 10)
        );

        statsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Library Statistics"
                )
        );

        JLabel booksLabel =
                new JLabel(
                        "<html><center>Total Books<br>"
                                + "<b>" + totalBooks + "</b>"
                                + "</center></html>",
                        SwingConstants.CENTER
                );

        JLabel readersLabel =
                new JLabel(
                        "<html><center>Total Readers<br>"
                                + "<b>" + totalReaders + "</b>"
                                + "</center></html>",
                        SwingConstants.CENTER
                );

        JLabel issuedLabel =
                new JLabel(
                        "<html><center>Currently Issued<br>"
                                + "<b>" + issuedBooks + "</b>"
                                + "</center></html>",
                        SwingConstants.CENTER
                );

        statsPanel.add(booksLabel);
        statsPanel.add(readersLabel);
        statsPanel.add(issuedLabel);


        // =========================
        // MENU BUTTONS
        // =========================

        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 2, 15, 15)
        );

        buttonPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Library Operations"
                )
        );

        bookButton =
                new JButton("Book Management");

        readerButton =
                new JButton("Reader Management");

        issueButton =
                new JButton("Issue / Return Book");

        logoutButton =
                new JButton("Logout");


        // Book Management
        bookButton.addActionListener(e -> {

            BookFrame bookFrame =
                    new BookFrame();

            bookFrame.setVisible(true);
        });


        // Reader Management
        readerButton.addActionListener(e -> {

            ReaderFrame readerFrame =
                    new ReaderFrame();

            readerFrame.setVisible(true);
        });


        // Issue / Return
        issueButton.addActionListener(e -> {

            IssueFrame issueFrame =
                    new IssueFrame();

            issueFrame.setVisible(true);
        });


        // Logout
        logoutButton.addActionListener(e -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            this.dispose();
        });


        buttonPanel.add(bookButton);
        buttonPanel.add(readerButton);
        buttonPanel.add(issueButton);
        buttonPanel.add(logoutButton);


        // =========================
        // ADD TO MAIN PANEL
        // =========================

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                statsPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DashboardFrame dashboardFrame =
                    new DashboardFrame("Library Admin");

            dashboardFrame.setVisible(true);
        });
    }
}
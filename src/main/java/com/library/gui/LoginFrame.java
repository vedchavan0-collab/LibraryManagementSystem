package com.library.gui;

import javax.swing.*;
import com.library.dao.StaffDAO;
import com.library.model.Staff;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("Library Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {

            String username = usernameField.getText();

            String password =
                    new String(passwordField.getPassword());

            StaffDAO staffDAO = new StaffDAO();

            Staff staff = staffDAO.login(username, password);

            if (staff != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!\nWelcome, " + staff.getName()
                );

                DashboardFrame dashboardFrame =
                        new DashboardFrame(staff.getName());

                dashboardFrame.setVisible(true);

                this.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password."
                );
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);

        add(panel);
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}
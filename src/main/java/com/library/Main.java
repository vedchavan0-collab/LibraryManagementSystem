package com.library;

import com.library.dao.StaffDAO;
import com.library.model.Staff;

public class Main {

    public static void main(String[] args) {

        StaffDAO staffDAO = new StaffDAO();

        Staff staff = staffDAO.login("admin", "admin123");

        if (staff != null) {

            System.out.println("Login successful!");
            System.out.println("Welcome, " + staff.getName());

        } else {

            System.out.println("Invalid username or password.");
        }
    }
}
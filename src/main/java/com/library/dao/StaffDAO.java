package com.library.dao;

import com.library.model.Staff;
import com.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO {

    public Staff login(String username, String password) {

        String sql = "SELECT * FROM staff " +
                "WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Staff staff = new Staff();

                staff.setStaffId(resultSet.getInt("staff_id"));
                staff.setUsername(resultSet.getString("username"));
                staff.setPassword(resultSet.getString("password"));
                staff.setName(resultSet.getString("name"));
                staff.setEmail(resultSet.getString("email"));

                return staff;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
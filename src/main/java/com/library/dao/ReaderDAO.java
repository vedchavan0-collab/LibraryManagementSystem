package com.library.dao;

import com.library.model.Reader;
import com.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {

    public boolean addReader(Reader reader) {

        String sql = "INSERT INTO readers " +
                "(name, email, phone, address, registration_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reader.getName());
            statement.setString(2, reader.getEmail());
            statement.setString(3, reader.getPhone());
            statement.setString(4, reader.getAddress());
            statement.setDate(5,
                    java.sql.Date.valueOf(reader.getRegistrationDate()));

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reader> getAllReaders() {

        List<Reader> readers = new ArrayList<>();

        String sql = "SELECT * FROM readers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Reader reader = new Reader();

                reader.setReaderId(resultSet.getInt("reader_id"));
                reader.setName(resultSet.getString("name"));
                reader.setEmail(resultSet.getString("email"));
                reader.setPhone(resultSet.getString("phone"));
                reader.setAddress(resultSet.getString("address"));

                java.sql.Date date =
                        resultSet.getDate("registration_date");

                if (date != null) {
                    reader.setRegistrationDate(date.toLocalDate());
                }

                readers.add(reader);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readers;
    }
    public List<Reader> searchReaders(String keyword) {

        List<Reader> readers = new ArrayList<>();

        String sql = "SELECT * FROM readers " +
                "WHERE name LIKE ? OR email LIKE ? OR phone LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Reader reader = new Reader();

                reader.setReaderId(resultSet.getInt("reader_id"));
                reader.setName(resultSet.getString("name"));
                reader.setEmail(resultSet.getString("email"));
                reader.setPhone(resultSet.getString("phone"));
                reader.setAddress(resultSet.getString("address"));

                java.sql.Date date =
                        resultSet.getDate("registration_date");

                if (date != null) {
                    reader.setRegistrationDate(date.toLocalDate());
                }

                readers.add(reader);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readers;
    }
    public boolean updateReader(Reader reader) {

        String sql = "UPDATE readers SET " +
                "name = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "address = ?, " +
                "registration_date = ? " +
                "WHERE reader_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reader.getName());
            statement.setString(2, reader.getEmail());
            statement.setString(3, reader.getPhone());
            statement.setString(4, reader.getAddress());
            statement.setDate(5,
                    java.sql.Date.valueOf(reader.getRegistrationDate()));
            statement.setInt(6, reader.getReaderId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteReader(int readerId) {

        String sql = "DELETE FROM readers WHERE reader_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, readerId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
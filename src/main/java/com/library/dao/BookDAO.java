package com.library.dao;

import com.library.model.Book;
import com.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public boolean addBook(Book book) {

        String sql = "INSERT INTO books " +
                "(title, author, publisher, category, quantity, available_quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setString(4, book.getCategory());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getAvailableQuantity());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book();

                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setCategory(resultSet.getString("category"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setAvailableQuantity(
                        resultSet.getInt("available_quantity")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    public List<Book> searchBooks(String keyword) {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books " +
                "WHERE title LIKE ? OR author LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Book book = new Book();

                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setCategory(resultSet.getString("category"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setAvailableQuantity(
                        resultSet.getInt("available_quantity")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    public boolean updateBook(Book book) {

        String sql = "UPDATE books SET " +
                "title = ?, " +
                "author = ?, " +
                "publisher = ?, " +
                "category = ?, " +
                "quantity = ?, " +
                "available_quantity = ? " +
                "WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setString(4, book.getCategory());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getAvailableQuantity());
            statement.setInt(7, book.getBookId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBook(int bookId) {

        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Book> getAvailableBooks() {

        List<Book> books = new ArrayList<>();

        String sql =
                "SELECT * FROM books " +
                        "WHERE available_quantity > 0";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book();

                book.setBookId(
                        resultSet.getInt("book_id")
                );

                book.setTitle(
                        resultSet.getString("title")
                );

                book.setAuthor(
                        resultSet.getString("author")
                );

                book.setPublisher(
                        resultSet.getString("publisher")
                );

                book.setCategory(
                        resultSet.getString("category")
                );

                book.setQuantity(
                        resultSet.getInt("quantity")
                );

                book.setAvailableQuantity(
                        resultSet.getInt("available_quantity")
                );

                books.add(book);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return books;
    }
    public int getAvailableQuantity(int bookId) {

        String sql =
                "SELECT available_quantity " +
                        "FROM books " +
                        "WHERE book_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("available_quantity");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;
    }
}
package com.library.dao;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import com.library.model.IssueTransaction;
import com.library.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssueDAO {

    public boolean issueBook(IssueTransaction transaction) {

        String checkBookSql =
                "SELECT available_quantity FROM books " +
                        "WHERE book_id = ? FOR UPDATE";

        String checkReaderSql =
                "SELECT reader_id FROM readers " +
                        "WHERE reader_id = ?";

        String insertIssueSql =
                "INSERT INTO issue_transactions " +
                        "(book_id, reader_id, issue_date, due_date, status) " +
                        "VALUES (?, ?, ?, ?, ?)";

        String updateBookSql =
                "UPDATE books SET available_quantity = " +
                        "available_quantity - 1 " +
                        "WHERE book_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);
            // Check whether the reader exists
            try (PreparedStatement readerStatement =
                         connection.prepareStatement(checkReaderSql)) {

                readerStatement.setInt(
                        1,
                        transaction.getReaderId()
                );

                ResultSet readerResult =
                        readerStatement.executeQuery();

                if (!readerResult.next()) {

                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement checkStatement =
                         connection.prepareStatement(checkBookSql)) {

                checkStatement.setInt(
                        1,
                        transaction.getBookId()
                );

                ResultSet resultSet =
                        checkStatement.executeQuery();

                if (!resultSet.next()) {

                    connection.rollback();
                    return false;
                }

                int availableQuantity =
                        resultSet.getInt("available_quantity");

                if (availableQuantity <= 0) {

                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement insertStatement =
                         connection.prepareStatement(insertIssueSql)) {

                insertStatement.setInt(
                        1,
                        transaction.getBookId()
                );

                insertStatement.setInt(
                        2,
                        transaction.getReaderId()
                );

                insertStatement.setDate(
                        3,
                        java.sql.Date.valueOf(
                                transaction.getIssueDate()
                        )
                );

                insertStatement.setDate(
                        4,
                        java.sql.Date.valueOf(
                                transaction.getDueDate()
                        )
                );

                insertStatement.setString(
                        5,
                        transaction.getStatus()
                );

                insertStatement.executeUpdate();
            }

            try (PreparedStatement updateStatement =
                         connection.prepareStatement(updateBookSql)) {

                updateStatement.setInt(
                        1,
                        transaction.getBookId()
                );

                updateStatement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
    public List<IssueTransaction> getIssuedBooks() {

        List<IssueTransaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM issue_transactions " +
                "WHERE status = 'Issued'";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                IssueTransaction transaction =
                        new IssueTransaction();

                transaction.setIssueId(
                        resultSet.getInt("issue_id")
                );

                transaction.setBookId(
                        resultSet.getInt("book_id")
                );

                transaction.setReaderId(
                        resultSet.getInt("reader_id")
                );

                transaction.setIssueDate(
                        resultSet.getDate("issue_date")
                                .toLocalDate()
                );

                transaction.setDueDate(
                        resultSet.getDate("due_date")
                                .toLocalDate()
                );

                transaction.setStatus(
                        resultSet.getString("status")
                );

                transactions.add(transaction);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return transactions;
    }

    public boolean returnBook(int issueId) {

        String findIssueSql =
                "SELECT book_id FROM issue_transactions " +
                        "WHERE issue_id = ? AND status = 'Issued'";

        String updateIssueSql =
                "UPDATE issue_transactions " +
                        "SET return_date = ?, status = 'Returned' " +
                        "WHERE issue_id = ?";

        String updateBookSql =
                "UPDATE books " +
                        "SET available_quantity = available_quantity + 1 " +
                        "WHERE book_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            int bookId;

            // Find the book connected to this issue
            try (PreparedStatement statement =
                         connection.prepareStatement(findIssueSql)) {

                statement.setInt(1, issueId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (!resultSet.next()) {
                    connection.rollback();
                    return false;
                }

                bookId = resultSet.getInt("book_id");
            }

            // Mark the transaction as Returned
            try (PreparedStatement statement =
                         connection.prepareStatement(updateIssueSql)) {

                statement.setDate(
                        1,
                        java.sql.Date.valueOf(LocalDate.now())
                );

                statement.setInt(2, issueId);

                statement.executeUpdate();
            }

            // Increase available book quantity
            try (PreparedStatement statement =
                         connection.prepareStatement(updateBookSql)) {

                statement.setInt(1, bookId);

                statement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
    public java.util.List<String> getAllIssuedBooks() {

        java.util.List<String> issuedBooks =
                new java.util.ArrayList<>();

        String sql =
                "SELECT i.issue_id, b.title, r.name, " +
                        "i.issue_date, i.due_date, i.status " +
                        "FROM issue_transactions i " +
                        "JOIN books b ON i.book_id = b.book_id " +
                        "JOIN readers r ON i.reader_id = r.reader_id " +
                        "ORDER BY i.issue_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                String details =
                        "Issue ID: " + resultSet.getInt("issue_id") +
                                "\nBook: " + resultSet.getString("title") +
                                "\nReader: " + resultSet.getString("name") +
                                "\nIssue Date: " + resultSet.getDate("issue_date") +
                                "\nDue Date: " + resultSet.getDate("due_date") +
                                "\nStatus: " + resultSet.getString("status") +
                                "\n--------------------";

                issuedBooks.add(details);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issuedBooks;
    }
}
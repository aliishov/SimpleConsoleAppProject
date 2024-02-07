package database;

import controller.Authors;
import model.Author;
import model.Book;
import model.Genre;

import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DatabaseOperations {
    private Connection connection;
    //--------------------------------------------------------------------------
    public DatabaseOperations()
    {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
        }
    }
    //--------------------------------------------------------------------------
    private void saveAuthorsToDatabase(Set<Author> authors)
    {
        try {
            String query = "INSERT INTO Authors (author_id, author_name, author_surname, birth_year) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            for (Author author : authors)
            {
                statement.setInt(1, author.getId());
                statement.setString(2, author.getName());
                statement.setString(3, author.getSurname());
                statement.setDate(4, new Date(author.getBirthYear().getTime()));
                statement.executeUpdate();
            }
            System.out.println("Authors saved to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while saving authors to database.");
        }
    }
    //--------------------------------------------------------------------------
    public void saveBooksToDatabase(Set<Book> books) {
        try {
            for (Book book : books)
            {
                Author author = book.getBookAuthor();
                if (!Authors.isAuthorExists(author.getId()))
                    saveAuthorsToDatabase(Collections.singleton(author));
            }
            String insertQuery = "INSERT INTO Books (book_id, book_name, page_count, release_date, genre, author_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            for (Book book : books)
            {
                if (!isBookExists(connection, book))
                {
                    statement.setInt(1, book.getId());
                    statement.setString(2, book.getName());
                    statement.setInt(3, book.getPageCount());
                    statement.setDate(4, new Date(book.getReleaseYear().getTime()));
                    statement.setString(5, book.getGenre().toString());
                    statement.setInt(6, book.getBookAuthor().getId());
                    statement.executeUpdate();
                }
            }
            System.out.println("Books saved to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while saving books to database.");
        }
    }

    private boolean isBookExists(Connection connection, Book book) throws SQLException
    {
        String query = "SELECT COUNT(*) FROM Books WHERE book_name = ? AND page_count = ? AND release_date = ? AND genre = ? AND author_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, book.getName());
        statement.setInt(2, book.getPageCount());
        statement.setDate(3, new Date(book.getReleaseYear().getTime()));
        statement.setString(4, book.getGenre().toString());
        statement.setInt(5, book.getBookAuthor().getId());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    }

    //--------------------------------------------------------------------------
    public Set<Book> getAllBooksFromDatabase()
    {
        Set<Book> books = new HashSet<>();
        Authors authorsController = new Authors();
        authorsController.addAllAuthors(getAllAuthorsFromDatabase());

        try {
            String query = "SELECT * FROM Books";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("book_id");
                String name = resultSet.getString("book_name");
                int pageCount = resultSet.getInt("page_count");
                java.sql.Date releaseDate = resultSet.getDate("release_date");
                String genre = resultSet.getString("genre");
                int authorId = resultSet.getInt("author_id");

                Author author = authorsController.getAuthorById(authorId);

                Book book = new Book(id, name, pageCount, Genre.valueOf(genre), releaseDate, author);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting books from database.");
        }
        return books;
    }
    //--------------------------------------------------------------------------
    public Set<Author> getAllAuthorsFromDatabase()
    {
        Set<Author> authors = new HashSet<>();

        try {
            String query = "SELECT * FROM Authors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("author_id");
                String name = resultSet.getString("author_name");
                String surname = resultSet.getString("author_surname");
                java.sql.Date birthDate = resultSet.getDate("birth_year");

                Author author = new Author(id, name, surname, birthDate);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting authors from database.");
        }
        return authors;
    }
    //--------------------------------------------------------------------------
    public void removeAllBooksFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Books");
            System.out.println("All books removed from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while removing books from the database.");
        }
    }
    //--------------------------------------------------------------------------
    public void removeAllAuthorsFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Authors");
            System.out.println("All authors removed from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while removing authors from the database.");
        }
    }
    //--------------------------------------------------------------------------
    public void editAuthorById(int id, String newName, String newSurname,  java.util.Date newBirthYear) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            String query = "UPDATE Authors SET author_name = ?, author_surname = ?, birth_year = ? WHERE author_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, newSurname);
            statement.setDate(3, new java.sql.Date(newBirthYear.getTime()));
            statement.setInt(4, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Author with ID " + id + " updated successfully.");
                connection.commit();
            }
            else
            {
                System.out.println("Author with ID " + id + " not found.");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while updating author with ID " + id);
            connection.rollback();
        } finally {
            if (connection != null)
            {
                try {
                    connection.setAutoCommit(true); // Возвращаем автоматическое управление транзакциями
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //--------------------------------------------------------------------------
    public void editBookById(int id, String newName, int newPageCount, java.util.Date newReleaseYear, Genre newGenre, Author newAuthor) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            Book book = new Book(id, newName, newPageCount, newGenre, newReleaseYear, newAuthor);

            if (!isBookExists(connection, book))
            {
                System.out.println("Book with ID " + id + " not found.");
                return;
            }

            String query = "UPDATE Books SET book_name = ?, page_count = ?, release_date = ?, genre = ?, author_id = ? WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setInt(2, newPageCount);
            statement.setDate(3, new java.sql.Date(newReleaseYear.getTime()));
            statement.setString(4, newGenre.toString());
            statement.setInt(5, newAuthor.getId());
            statement.setInt(6, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Book with ID " + id + " updated successfully.");
                connection.commit();
            }
            else
            {
                System.out.println("Failed to update book with ID " + id);
                connection.rollback();
            }
        } catch (SQLException e) {
            if (connection != null)
                connection.rollback();

            e.printStackTrace();
            System.out.println("Error while updating book with ID " + id);
        } finally {
            if (connection != null)
            {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

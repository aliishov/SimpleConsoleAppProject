package view;

import controller.Authors;
import controller.Books;
import database.DatabaseOperations;
import model.Author;
import model.Book;
import model.Genre;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class UserOutputHandler
{
    public static void displayMenu()
    {
        System.out.println("===================Command Menu===================");
        System.out.println("1. Add Book");
        System.out.println("2. Add Author");
        System.out.println("3. Edit Book By Id");
        System.out.println("4. Edit Author By Id");
        System.out.println("5. Get Book By Id");
        System.out.println("6. Get Author By Id");
        System.out.println("7. Remove Book By Id");
        System.out.println("8. Remove Author By Id");
        System.out.println("9. Get All Books");
        System.out.println("10. Get All Authors");
        System.out.println("11. Save");
        System.out.println("12. Close");
        System.out.println("==================================================");
        System.out.print("Enter the command number to execute: ");
    }
    //-------------------------------------------------
    public static void displayAuthor(Authors authors)
    {
        System.out.print("Enter the ID of the author you want to receive: ");
        int id = UserInputHandler.getInt();
        if (authors.getAuthorById(id) == null)
        {
            System.out.println("Author with ID " + id + " not found");
            return;
        }
        else
            System.out.println(authors.getAuthorById(id));
    }
    //-------------------------------------------------
    public static void displayBook(Books books)
    {
        System.out.print("Enter the ID of the book you want to receive: ");
        int id = UserInputHandler.getInt();
        if (books.getBookById(id) == null)
        {
            System.out.print("Book with ID " + id + " not found");
            return;
        }
        else
            System.out.println(books.getBookById(id));
    }
    //-------------------------------------------------
    public static void displayBooks(Books books)
    { books.getAllBooks(); }
    //-------------------------------------------------
    public static void displayAuthors(Authors authors)
    { authors.getAllAuthors(); }
    //-------------------------------------------------
    public static void removeAuthor(Authors authors)
    {
        System.out.print("Enter the ID of the author you want to remove: ");
        int id = UserInputHandler.getInt();
        authors.removeAuthorById(id);
    }
    //-------------------------------------------------
    public static void removeBook(Books books)
    {
        System.out.print("Enter the ID of the book you want to remove: ");
        int id = UserInputHandler.getInt();
        books.removeBookById(id);
    }
    //-------------------------------------------------
    public static void editAuthor(Authors authors, DatabaseOperations dbOperations) throws IOException, SQLException
    {
        System.out.print("Enter the ID of the author you want to change: ");
        int id = UserInputHandler.getInt();

        Author author = authors.getAuthorById(id);
        if (author == null)
        {
            System.out.println("Author with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new Author name: ");
        String newAuthorName = UserInputHandler.getString();

        System.out.print("Enter new Author surname: ");
        String newAuthorSurname = UserInputHandler.getString();;

        System.out.println("Enter new Author year of birth");
        Date newAuthorYearOfBirth = UserInputHandler.getDateFromInput();

        authors.editAuthor(id, newAuthorName, newAuthorSurname, newAuthorYearOfBirth);

        dbOperations.editAuthorById(id, newAuthorName, newAuthorSurname, newAuthorYearOfBirth);

        System.out.println("Author with ID " + id + " successfully edited.");
    }
    //-------------------------------------------------
    public static void editBook(Books books, DatabaseOperations dbOperations) throws IOException, SQLException
    {
        System.out.print("Enter the ID of the book you want to change: ");
        int id = UserInputHandler.getInt();

        Book book = books.getBookById(id);
        if (book == null)
        {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new Book name: ");
        String newName = UserInputHandler.getString();

        System.out.print("Enter new Book page count: ");
        int newPageCount = UserInputHandler.getInt();

        System.out.println("Enter new Book release date");
        Date newYearOfIssue = UserInputHandler.getDateFromInput();

        System.out.print("Enter new Book genre: ");
        String newGenreStr = UserInputHandler.getString();
        Genre newGenre = Genre.valueOf(newGenreStr);

        Author newAuthor = createAuthorWithId();

        books.editBook(id, newName, newPageCount, newYearOfIssue, newGenre, newAuthor);

        dbOperations.editBookById(id, newName, newPageCount, newYearOfIssue, newGenre, newAuthor);

        System.out.println("Book with ID " + id + " successfully edited.");
    }
    //-------------------------------------------------
    public static Book createBookWithId() throws IOException
    {
        System.out.print("Enter book id: ");
        int id = UserInputHandler.getInt();

        System.out.print("Enter book name: ");
        String name = UserInputHandler.getString();

        System.out.print("Enter count of book pages: ");
        int pageCount = UserInputHandler.getInt();

        System.out.print("Enter book genre: ");
        String genreStr = UserInputHandler.getString();
        Genre genre = Genre.valueOf(genreStr);

        System.out.println("Enter release date");
        Date releaseDate = UserInputHandler.getDateFromInput();

        Author author = createAuthorWithId();

        return new Book(id, name, pageCount, genre, releaseDate, author);
    }
    //-------------------------------------------------
    public static Author createAuthorWithId() throws IOException
    {
        System.out.print("Enter author id: ");
        int id = UserInputHandler.getInt();

        System.out.print("Enter author's name: ");
        String name = UserInputHandler.getString();

        System.out.print("Enter author's surname: ");
        String surname = UserInputHandler.getString();

        System.out.println("Enter the date of birth");
        java.util.Date birthDate = UserInputHandler.getDateFromInput();

        return new Author(id, name, surname, birthDate);
    }
}


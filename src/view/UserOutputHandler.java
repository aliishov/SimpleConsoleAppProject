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
        System.out.println("==============Command Menu==============");
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
        System.out.println("========================================");
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
            System.out.println("Book with ID " + id + " not found");
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
        DatabaseOperations.removeAuthorFromDatabase(String.valueOf(id));
    }
    //-------------------------------------------------
    public static void removeBook(Books books)
    {
        System.out.print("Enter the ID of the book you want to remove: ");
        int id = UserInputHandler.getInt();
        books.removeBookById(id);
        DatabaseOperations.removeBookFromDatabase(String.valueOf(id));
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

        getSetGenreMenu();
        int genreId = 0;
        while (genreId < 1 || genreId > 12) {
            System.out.print("Enter a number between 1 - 12 to set the genre: ");
            genreId = UserInputHandler.getInt();
            if (genreId < 1 || genreId > 12) {
                System.out.println("Invalid entry! Enter a number between 1 - 12.");
            }
        }
        Genre newGenre = setGenre(genreId);

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

        getSetGenreMenu();
        int genreId = 0;
        while (genreId < 1 || genreId > 12) {
            System.out.print("Enter a number between 1 - 12 to set the genre: ");
            genreId = UserInputHandler.getInt();
            if (genreId < 1 || genreId > 12) {
                System.out.println("Invalid entry! Enter a number between 1 - 12.");
            }
        }
        Genre genre = setGenre(genreId);

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
    //-------------------------------------------------
    public static void closeMenu()
    {
        System.out.println("All unsaved data wil be deleted!");
        System.out.println("=====Choose option=====");
        System.out.println("1. Close anyway");
        System.out.println("2. Save and Close");
        System.out.println("=======================");
    }
    //-------------------------------------------------
    public static int close()
    {
        int num = 0;
        while (num != 1 && num != 2)
        {
            System.out.print("Enter 1 to continue or 2 to save and exit: ");
            num = UserInputHandler.getInt();
            if (num != 1 && num != 2)
                System.out.println("Invalid entry! Enter 1 or 2.");
        }
        return num;
    }
    //-------------------------------------------------
    private static void getSetGenreMenu()
    {
        System.out.println("==============Genre Menu==============");
        System.out.println("1. Novel");
        System.out.println("2. Mystery");
        System.out.println("3. Thriller");
        System.out.println("4. Fantasy");
        System.out.println("5. ScienceFiction");
        System.out.println("6. Horror");
        System.out.println("7. HistoricalNovel");
        System.out.println("8. PopularScience");
        System.out.println("9. Classics");
        System.out.println("10. Poetry");
        System.out.println("11. BiographiesAndMemoirs");
        System.out.println("12. PsychologicalNovel");
        System.out.println("========================================");
    }
    //-------------------------------------------------
    private static Genre setGenre(int id)
    { return Genre.getGenreById(id); }
    //-------------------------------------------------
}


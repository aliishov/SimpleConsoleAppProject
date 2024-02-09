package view;

import controller.Authors;
import controller.Books;
import database.DatabaseOperations;
import model.Author;
import model.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class BookManagerMain
{
    private static Authors authors = new Authors();
    private static Books books = new Books();
    private static DatabaseOperations dbOperations = new DatabaseOperations();
    //-------------------------------------------------
    public static void main(String[] args) throws IOException, SQLException {
        Set<Book> booksFromDB = dbOperations.getAllBooksFromDatabase();

        if (booksFromDB.isEmpty())
        {
            System.out.println("No books found in the database. Starting book creation process.");
            Book book = UserOutputHandler.createBookWithId();
            authors.addAuthor(book.getBookAuthor());
            books.addBook(book);
        }
        else
        {
            System.out.println("Books found in the database. Adding them to the collection.");
            books.addAllBooks(booksFromDB);
        }

        Author newAuthor;
        Book newBook;
        while (true)
        {
            UserOutputHandler.displayMenu();
            int choice = UserInputHandler.getInt();
            switch (choice)
            {
                case 1:
                    newBook = UserOutputHandler.createBookWithId();
                    books.addBook(newBook);
                    authors.addAuthor(newBook.getBookAuthor());
                    break;
                case 2:
                    newAuthor = UserOutputHandler.createAuthorWithId();
                    authors.addAuthor(newAuthor);
                    break;
                case 3:
                    UserOutputHandler.editBook(books, dbOperations);
                    break;
                case 4:
                    UserOutputHandler.editAuthor(authors, dbOperations);
                    break;
                case 5:
                    UserOutputHandler.displayBook(books);
                    break;
                case 6:
                    UserOutputHandler.displayAuthor(authors);
                    break;
                case 7:
                    UserOutputHandler.removeBook(books);
                    break;
                case 8:
                    UserOutputHandler.removeAuthor(authors);
                    break;
                case 9:
                    UserOutputHandler.displayBooks(books);
                    break;
                case 10:
                    UserOutputHandler.displayAuthors(authors);
                    break;
                case 11:
                    dbOperations.saveBooksToDatabase(books.getBooksList());
                    break;
                case 12:
                    UserOutputHandler.closeMenu();
                    if (UserOutputHandler.close() == 2)
                    {
                        dbOperations.saveBooksToDatabase(books.getBooksList());
                        return;
                    }
                    else
                        return;
                default:
                    System.out.println("Invalid Entry! Please try again.");
                    break;
            }
        }
    }
}

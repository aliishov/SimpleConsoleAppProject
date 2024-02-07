package controller;

import model.Author;
import model.Book;
import model.Genre;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Books
{
    private Set<Book> books;
    //------------------------------------------------------------------
    public Books()                      // Constructor
    { books = new HashSet<>(); }
    //------------------------------------------------------------------
    public void addBook(Book book)      // Add a book to the set
    { books.add(book); }
    //------------------------------------------------------------------
    public Book getBookById(int id)     // Get book by ID
    {
        for (Book book : books)
            if (book.getId() == id)
                return book;
        return null;
    }
    //------------------------------------------------------------------
    public void getAllBooks()           // Print all books
    {
        if (!isEmpty())
        {
            int id = 1;
            System.out.println("Books: ");
            for (Book book : books)
                System.out.println(id++ + ". { " + book + " }");
        }
        else
            System.out.println("No books found.");
    }
    //------------------------------------------------------------------
    public void removeBookById(int id)      // Remove book by ID
    {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext())
        {
            Book book = iterator.next();
            if (book.getId() == id)
            {
                System.out.println("Book: " + book.getName() + " removed!");
                iterator.remove();
                return;
            }
        }
        System.out.println("Book with id " + id + " not found.");
    }
    //------------------------------------------------------------------
                                            // Edit book information
    public void editBook(int id, String name, int pageCount, Date yearOfIssue,
                         Genre bookGenre, Author author)
    {
        for (Book book : books)
        {
            if (book.getId() == id)
            {
                book.setName(name);
                book.setPageCount(pageCount);
                book.setReleaseYear(yearOfIssue);
                book.setGenre(bookGenre);
                book.setBookAuthor(author);
                System.out.println("Book with id " + id + " changed.");
                return;
            }
        }
        System.out.println("Book with id " + id + " not found.");
    }
    //------------------------------------------------------------------
    public void addAllBooks(Set<Book> newBooks)
    { books.addAll(newBooks); }                 // Add all books from another set
    //------------------------------------------------------------------
    public Set<Book> getBooksList()             // Get the set of books
    { return books; }
    //------------------------------------------------------------------
    public boolean isEmpty()
    { return (books.size() == 0); }
}

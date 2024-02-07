package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book
{
    private int id;             // Book ID
    private String name;        // Book name
    private int pageCount;      // Number of pages in the book
    private Genre genre;        // Genre of the book
    private Date releaseYear;   // Release year of the book
    private Author bookAuthor;  // Author of the book
    //-------------------------------------------------
    // Constructor
    public Book(int id, String name, int pageCount, Genre genre, Date releaseYear, Author bookAuthor)
    {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.bookAuthor = bookAuthor;
    }
    //-------------------------------------------------
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Date getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Date releaseYear) { this.releaseYear = releaseYear;}

    public Author getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(Author bookAuthor) { this.bookAuthor = bookAuthor; }
    //-------------------------------------------------
    // ToString method
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(releaseYear);

        String authorName = (bookAuthor != null) ? bookAuthor.getName() + " " + bookAuthor.getSurname() : "Unknown Author";

        return "Name: " + name + ", Page count: " + pageCount +
                ", Genre: " + genre.toString() + ", Release year: " + year +
                ", Book Author: " + authorName;
    }
}

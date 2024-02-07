package controller;

import database.DatabaseConnection;
import model.Author;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Authors
{
    private static Set<Author> authors;
    //------------------------------------------------------------------
    public Authors()                            // Constructor
    { authors = new HashSet<>(); }
    //------------------------------------------------------------------
    public void addAuthor(Author author)        // Add an author to the set
    { authors.add(author); }
    //------------------------------------------------------------------
    public static Author getAuthorById(int id)  // Get author by ID
    {
        for (Author author : authors)
            if (author.getId() == id)
                return author;
        return null;
    }
    //------------------------------------------------------------------
    public void getAllAuthors()                  // Print all authors
    {
        if (!isEmpty())
        {
            int id = 1;
            System.out.println("Authors: ");
            for (Author author : authors)
                System.out.println(id++ + ". { " + author + " }");
        }
        else
            System.out.println("No authors found.");
    }
    //------------------------------------------------------------------
    public void removeAuthorById(int id) {      // Remove author by ID
        Iterator<Author> iterator = authors.iterator();
        while (iterator.hasNext())
        {
            Author author = iterator.next();
            if (author.getId() == id)
            {
                System.out.println("Author: " + author.getName() + " " +
                        author.getSurname() + " removed!");
                iterator.remove();
                return;
            }
        }
        System.out.println("Author with id " + id + " not found.");
    }
    //------------------------------------------------------------------
                                                // Edit author information
    public void editAuthor(int id, String name, String surname, Date birthYear)
    {
        for (Author author : authors)
        {
            if (author.getId() == id)
            {
                author.setName(name);
                author.setSurname(surname);
                author.setBirthYear(birthYear);
                System.out.println("Author with id " + id + " changed.");
                return;
            }
        }
        System.out.println("Author with id " + id + " not found.");
    }
    //------------------------------------------------------------------
    public Set<Author> getAuthorsList()          // Get the set of authors
    { return authors; }
    //------------------------------------------------------------------
                                                // Check if an author exists by ID
    public static boolean isAuthorExists(int authorId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM Authors WHERE author_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while checking author existence.");
            return false;
        }
    }
    //------------------------------------------------------------------
    public void addAllAuthors(Set<Author> newAuthors)
    { authors.addAll(newAuthors); }                 // Add all authors from another set
    //------------------------------------------------------------------
    public boolean isEmpty()
    { return (authors.size() == 0); }
}

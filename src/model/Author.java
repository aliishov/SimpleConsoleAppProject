package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Author
{
    private int id;         // Author ID
    private String name;    // Author's name
    private String surname; // Author's surname
    private Date birthYear; // Author's birth year
    //-------------------------------------------------
    // Constructor
    public Author(int id, String name, String surname, Date birthYear)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
    }
    //-------------------------------------------------
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Date getBirthYear() { return birthYear; }
    public void setBirthYear(Date birthYear) { this.birthYear = birthYear; }
    //-------------------------------------------------
    // ToString method
    @Override
    public String toString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(birthYear);
        return "Name: " + name + ", Surname: " + surname + ", Birth year: " + year;
    }
}

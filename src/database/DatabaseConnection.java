package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    // Database connection URL
    private static final String URL = "jdbc:postgresql://localhost:5432/bookmanager";

    // Database username
    private static final String USER = "postgres";

    // Database password
    private static final String PASSWORD = "lu?s7D,c3AAM";

    // Get a connection to the database
    public static Connection getConnection() throws SQLException
    { return DriverManager.getConnection(URL, USER, PASSWORD); }
}

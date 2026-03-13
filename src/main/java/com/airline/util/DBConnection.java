package com.airline.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class that provides a JDBC connection to the airline_db MySQL database.
 * Place the MySQL Connector/J JAR inside WebContent/WEB-INF/lib/
 */
public class DBConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/airline_db?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";       // ← change to your MySQL username
    private static final String PASSWORD = "LUCIFER3004";       // ← change to your MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    /**
     * Returns a new Connection each time it is called.
     * Callers are responsible for closing the connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

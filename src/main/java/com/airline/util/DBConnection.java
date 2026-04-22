package com.airline.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://airline-mysql-slaughtergangsoulsnatcher69420-3600.h.aivencloud.com:20396/defaultdb?ssl-mode=REQUIRED&useSSL=true&serverTimezone=UTC";
    private static final String USER     = "avnadmin";
    private static final String PASSWORD = System.getenv("DB_PASSWORD"); 

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

package com.example.coursetrackingsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/course_tracking";
    private static final String USER = "admin"; //requires to be updated
    private static final String PASSWORD = "admin"; // requires to be updated

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

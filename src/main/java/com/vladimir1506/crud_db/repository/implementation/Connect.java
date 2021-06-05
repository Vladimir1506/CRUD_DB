package com.vladimir1506.crud_db.repository.implementation;

import java.sql.*;

public class Connect {
    private final String URL = "jdbc:mysql://localhost:3306/crud_db";
    private final String USERNAME = "rootroot";
    private final String PASSWORD = "rootroot";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    Connection connection;
    Statement statement;

    public Connect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public Statement getStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}

package com.vladimir1506.crud_db.repository.implementation;

import java.sql.*;

public class Connect {
    private static final String URL = "jdbc:mysql://localhost:3306/crud_db";
    private static final String USERNAME = "rootroot";
    private static final String PASSWORD = "rootroot";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    private Connect() {
    }

    public static PreparedStatement getStatement(String sql) {
        try {
            preparedStatement = getConnection().prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    private static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

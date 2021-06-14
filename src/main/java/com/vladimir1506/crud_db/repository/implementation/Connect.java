package com.vladimir1506.crud_db.repository.implementation;

import java.sql.*;

public class Connect {
    private static final String URL = "jdbc:mysql://localhost:3306/crud_db";
    private static final String USERNAME = "rootroot";
    private static final String PASSWORD = "rootroot";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static PreparedStatement statement;

    private Connect() {
    }

    public static PreparedStatement getStatement(String sql) {
        if (statement == null) {
            try {
                Class.forName(DRIVER);
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                statement = connection.prepareStatement(sql);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return statement;
    }
}

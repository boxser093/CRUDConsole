package com.ilya.crudapp.connectbd;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private static MySQLConnector connector;
    private final String userName = "root";
    private final String password = "228228";
    private String connectionUrl = "jdbc:mysql://localhost:3306/tests1";

    public Connection getConnetion() {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private MySQLConnector() {

    }

    public static MySQLConnector getMySQLConnector() {
        if (connector == null){
            connector = new MySQLConnector();
        }
        return connector;
    }
}

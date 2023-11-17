package com.ilya.crudapp.connectbd;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class MySQLConnector {
    private static final String userName = "root";
    private static final String password = "228228";
    private static String connectionUrl = "jdbc:mysql://localhost:3306/tests1";
    private static Connection getConnetion() {
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

    public static PreparedStatement returnPrepareStatement(String sqlQuery){
        try {
            return getConnetion().prepareStatement(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static PreparedStatement returnPrepareStatement(String sqlQuery,int argument){
        try {
            return getConnetion().prepareStatement(sqlQuery,argument);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement returnStatement(){
        try {
            return getConnetion().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private MySQLConnector() {
    }

}

package com.ilya.crudapp.connectbd;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class PostgreSQLConnector {
    private static final String userName = "postgres";
    private static final String password = "1234";
    private static String connectionUrl = "jdbc:postgresql://localhost:5432/test1";
    private static Connection getConnetion() {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
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
    private PostgreSQLConnector() {
    }

}

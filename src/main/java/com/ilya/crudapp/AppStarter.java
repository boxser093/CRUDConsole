package com.ilya.crudapp;
import com.ilya.crudapp.connectbd.MySQLConnector;
import com.ilya.crudapp.view.ViewExecutor;

public class AppStarter {
    public static void main(String[] args) {
            MySQLConnector connector = MySQLConnector.getMySQLConnector();
            connector.getConnetion();
            ViewExecutor appRun = new ViewExecutor();
            appRun.run();
    }
}

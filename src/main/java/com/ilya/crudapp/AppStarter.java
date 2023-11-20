package com.ilya.crudapp;
import com.ilya.crudapp.view.ViewExecutor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AppStarter {
    public static void main(String[] args) {

            ViewExecutor appRun = new ViewExecutor();
            appRun.run();
    }
}

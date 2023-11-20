package com.ilya.crudapp.connectbd;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnetor {
//    @Getter
//    static SessionFactory sessionFactory;
//    Configuration configuration;
//
//    HibernateConnetor(){
//        configuration = new Configuration().configure("hibernate.cfg.xml");
//        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
//        serviceRegistryBuilder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//     }

    public static Session getCurrentSession() {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        return session;
    }
}

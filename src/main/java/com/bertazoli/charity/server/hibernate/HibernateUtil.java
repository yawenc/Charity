package com.bertazoli.charity.server.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.google.inject.Singleton;

@Singleton
public class HibernateUtil {
    
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            sessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistryBuilder.build());
        } catch (Exception e) {
            System.out.println("Failed to initialise session factory " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

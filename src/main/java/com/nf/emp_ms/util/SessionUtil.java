package com.nf.emp_ms.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class SessionUtil {

    private SessionFactory sessionFactory;
    private static SessionUtil instance;

    private SessionUtil() {
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionUtil getInstance() {
        if(instance == null) {
            instance = new SessionUtil();
        }
        return instance;
    }

    public static SessionFactory getSessionFactory () {
        return getInstance().sessionFactory;
    }

    public static Session getSession () {
        return getSessionFactory().openSession();
    }
}

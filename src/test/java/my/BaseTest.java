package my;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

public class BaseTest {

    protected Session session;

    @Before
    public void init () {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
    }

    @After
    public void after () {
        if(session != null && session.isOpen()) {
            session.getTransaction().commit();
            session.close();
        }
    }
}
